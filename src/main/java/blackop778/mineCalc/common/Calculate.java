package blackop778.mineCalc.common;

import blackop778.mineCalc.MineCalc;
import blackop778.mineCalc.client.ClientProxy;
import blackop778.mineCalc.core.CalcExceptions;
import blackop778.mineCalc.core.CalcExceptions.*;
import blackop778.mineCalc.core.Calculator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.I18n;
import javax.annotation.Nonnull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"deprecation", "ConstantConditions"})
public class Calculate extends CommandBase {

    public static final Style redStyle = new Style().setColor(TextFormatting.RED);

    @Nonnull
    @Override
    public String getName() {
        // What must be typed in following the / to trigger the command
        return "calc";
    }

    /**
     * What is shown when "/help Calculate" is typed in
     */
    @Nonnull
    @Override
    public String getUsage(ICommandSender sender) {
        if (playerHasMod(sender.getCommandSenderEntity()))
            return "minecalc.calc.help";
        else
            return I18n.translateToLocal("minecalc.calc.help");
    }

    public ITextComponent calculate(ICommandSender sender, @Nonnull String[] args) {
        ITextComponent print;
        boolean useOOPS;
        double answer;

        if (args.length == 0)
            return new TextComponentTranslation("minecalc.calc.usage").setStyle(redStyle);

        if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")) {
            useOOPS = Boolean.valueOf(args[0]);
            args[0] = "";
        }
        else
        {
            useOOPS = true;
        }

        StringBuilder condensedMath = new StringBuilder();
        for (String s : args) {
            condensedMath.append(s);
        }

        try {
            answer = Calculator.evaluate(condensedMath.toString(), useOOPS, getLastOutput(sender), MCConfig.fancyRemainders);
            setLastOutput(sender, answer);
            if (answer % 1 == 0 && answer < Integer.MAX_VALUE && answer > Integer.MIN_VALUE) {
                int i = (int) answer;
                print = new TextComponentString(String.valueOf(i));
            } else {
                print = new TextComponentString(String.valueOf(answer));
            }
        } catch (ImaginaryNumberException e) {
            return new TextComponentTranslation("minecalc.calc.imaginaryException").setStyle(redStyle);
        } catch (DivisionException er) {
            return new TextComponentTranslation("minecalc.calc.divZeroException").setStyle(redStyle);
        } catch (OperatorException err) {
            return new TextComponentTranslation("minecalc.calc.symbolException").setStyle(redStyle)
                    .appendSibling(new TextComponentString(" %"))
                    .appendSibling(new TextComponentTranslation("minecalc.calc.symbolExceptionPartTwo"));
        } catch (PreviousOutputException erro) {
            return new TextComponentTranslation("minecalc.calc.previousOutputException").setStyle(redStyle);
        } catch (CustomFunctionException error) {
            return new TextComponentString(error.getMessage()).setStyle(redStyle);
        } catch (FancyRemainderException errors) {
            int num1 = (int) (errors.numerator / errors.denominator);
            double num2 = errors.numerator % errors.denominator;
            print = new TextComponentString(num1 + "R");
            if (num2 % 1 == 0) {
                int i = (int) num2;
                print.appendSibling(new TextComponentString(String.valueOf(i)));
            } else {
                print.appendSibling(new TextComponentString(String.valueOf(num2)));
            }
            setLastOutput(sender, num2);
        } catch (AllStandinsUsedException errorsA) {
            return new TextComponentTranslation("minecalc.calc.standInsException").setStyle(redStyle)
                    .appendSibling(new TextComponentString(errorsA.getLocalizedMessage()));
        } catch (MultiplePointsException errorsAr) {
            return new TextComponentTranslation("minecalc.calc.multiplePointsException").setStyle(redStyle);
        } catch (UsageException errorsAre) {
            return new TextComponentTranslation("minecalc.calc.usage").setStyle(redStyle);
        } catch (InvalidNumberException errorsAreF) {
            return new TextComponentTranslation("minecalc.calc.numberFormatException").setStyle(redStyle)
                    .appendSibling(new TextComponentString(errorsAreF.getMessage()));
        } catch (ParenthesisException errorsAreFu) {
            if (errorsAreFu.tooMany)
                return new TextComponentTranslation("minecalc.calc.manyParenthesisException").setStyle(redStyle);
            else
                return new TextComponentTranslation("minecalc.calc.fewParenthesisException").setStyle(redStyle);
        } catch (CalcExceptions errorsAreFun) {
            errorsAreFun.printStackTrace();
            return new TextComponentString(
                    "Error: An unknown error occurred" + (errorsAreFun.getLocalizedMessage() == null ? ""
                            : ". Message: " + errorsAreFun.getLocalizedMessage()));
        }

        // Prepend the arguments to the output, if configured to
        if (MCConfig.returnInput) {
            print = new TextComponentString(condensedMath + "=").appendSibling(print);
        }

        return print;
    }

    /**
     * @param sender Who sent the command
     * @return The last output the user received
     * @throws PreviousOutputException If there was no previous output
     */
    private double getLastOutput(@Nullable ICommandSender sender) throws PreviousOutputException {
        if (sender == null)
            if (Calculator.consoleLastOutput == null)
                throw new PreviousOutputException();
            else
                return Calculator.consoleLastOutput;
        else {
            Entity e = sender.getCommandSenderEntity();
            if (e == null)
                return Calculator.consoleLastOutput;
            IMineCalcCompound comp = e.getCapability(MineCalcCompoundProvider.MCC_CAP, null);
            // Not exactly sure how we could get here, but this seems like a safe bet for feedback
            if (comp == null)
                throw new PreviousOutputException();
            return comp.getLastNumber();
        }
    }

    private void setLastOutput(@javax.annotation.Nullable ICommandSender sender, double newOutput) {
        if (sender == null) {
            Calculator.consoleLastOutput = newOutput;
        } else {
            Entity e = sender.getCommandSenderEntity();
            if (e == null) {
                Calculator.consoleLastOutput = newOutput;
            } else {
                IMineCalcCompound comp = e.getCapability(MineCalcCompoundProvider.MCC_CAP, null);
                if(comp != null)
                    comp.setLastNumber(newOutput);
            }
        }
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        // A list of alternate command names
        return new ArrayList<String>(Arrays.asList("Calc", "calculate", "Calculate"));
    }

    @javax.annotation.Nullable
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length % 2 != 1) {
            return new ArrayList<String>(Arrays.asList("+", "-", "*", "/", "%", "^", "/--"));
        } else
            return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getName().equals("@")) {
            MineCalc.LOGGER.warn("Command blocks cannot use /calc");
        } else {
            ITextComponent output = calculate(sender, args);
            output = translateCheck(sender, output);

            // Send the message back to the user
            if (sender.getName().equals("Server")) {
                MineCalc.LOGGER.info(output.getUnformattedText());
            } else {
                EntityPlayer player = (EntityPlayer) sender;
                player.sendMessage(output);
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @javax.annotation.Nullable
    private ITextComponent translateCheck(ICommandSender sender, @Nonnull ITextComponent toOutput) {
        if (playerHasMod(sender.getCommandSenderEntity()))
            return toOutput;
        else {
            Iterator<ITextComponent> it = toOutput.iterator();
            ITextComponent toReturn = null;
            while (it.hasNext()) {
                ITextComponent current = it.next();
                if (current instanceof TextComponentString) {
                    if (toReturn == null) {
                        toReturn = new TextComponentString(current.getUnformattedText()).setStyle(current.getStyle());
                    } else {
                        toReturn.appendSibling(new TextComponentString(current.getUnformattedComponentText())
                                .setStyle(current.getStyle()));
                    }
                } else {
                    TextComponentTranslation tran = (TextComponentTranslation) current;
                    String text = I18n.translateToLocal(tran.getKey());
                    if (toReturn == null) {
                        toReturn = new TextComponentString(text).setStyle(tran.getStyle());
                    } else {
                        toReturn.appendSibling(new TextComponentString(text).setStyle(tran.getStyle()));
                    }
                }
            }

            return toReturn;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private boolean playerHasMod(@javax.annotation.Nullable Entity ent) {
        if (ClientProxy.isClientSide())
            return true;
        if (ent == null)
            return true;
        IMineCalcCompound comp = ent.getCapability(MineCalcCompoundProvider.MCC_CAP, null);
        if(comp != null)
            return comp.getHasMineCalc();
        else
            return false;
    }
}
