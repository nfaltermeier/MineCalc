package blackop778.mineCalc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import blackop778.mineCalc.MineCalc;
import blackop778.mineCalc.client.ClientProxy;
import blackop778.mineCalc.core.CalcExceptions;
import blackop778.mineCalc.core.CalcExceptions.AllStandinsUsedException;
import blackop778.mineCalc.core.CalcExceptions.CustomFunctionException;
import blackop778.mineCalc.core.CalcExceptions.DivisionException;
import blackop778.mineCalc.core.CalcExceptions.FancyRemainderException;
import blackop778.mineCalc.core.CalcExceptions.ImaginaryNumberException;
import blackop778.mineCalc.core.CalcExceptions.InvalidNumberException;
import blackop778.mineCalc.core.CalcExceptions.MultiplePointsException;
import blackop778.mineCalc.core.CalcExceptions.OperatorException;
import blackop778.mineCalc.core.CalcExceptions.ParenthesisException;
import blackop778.mineCalc.core.CalcExceptions.PreviousOutputException;
import blackop778.mineCalc.core.CalcExceptions.UsageException;
import blackop778.mineCalc.core.Calculator;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class Calculate extends CommandBase {

    public static final ChatStyle redStyle = new ChatStyle().setColor(EnumChatFormatting.RED);

    @Override
    public String getCommandName() {
	// What must be typed in following the / to trigger the command
	return "calc";
    }

    /**
     * What is shown when "/help Calculate" is typed in
     */
    @Override
    public String getCommandUsage(ICommandSender sender) {
	if (playerHasMod(sender.getCommandSenderEntity()))
	    return "minecalc.calc.help";
	else
	    return I18n.format("minecalc.calc.help");
    }

    public IChatComponent calculate(ICommandSender sender, String[] args) {
	IChatComponent print = null;
	boolean useOOPS;
	double answer;

	if (args.length == 0)
	    return new ChatComponentTranslation("minecalc.calc.usage").setChatStyle(redStyle);
	useOOPS = Boolean.valueOf(args[0]);
	if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")) {
	    args[0] = "";
	}
	String condensedMath = "";
	for (String s : args) {
	    condensedMath += s;
	}
	try {
	    answer = Calculator.evaluate(condensedMath, useOOPS, getLastOutput(sender), MCConfig.fancyRemainders);
	    setLastOutput(sender, answer);
	    if (answer % 1 == 0) {
		int i = (int) answer;
		print = new ChatComponentText(String.valueOf(i));
	    } else {
		print = new ChatComponentText(String.valueOf(answer));
	    }
	} catch (ImaginaryNumberException e) {
	    return new ChatComponentTranslation("minecalc.calc.imaginaryException").setChatStyle(redStyle);
	} catch (DivisionException er) {
	    return new ChatComponentTranslation("minecalc.calc.divZeroException").setChatStyle(redStyle);
	} catch (OperatorException err) {
	    return new ChatComponentTranslation("minecalc.calc.symbolException").setChatStyle(redStyle)
		    .appendSibling(new ChatComponentText(" %"))
		    .appendSibling(new ChatComponentTranslation("minecalc.calc.symbolExceptionPartTwo"));
	} catch (PreviousOutputException erro) {
	    return new ChatComponentTranslation("minecalc.calc.previousOutputException").setChatStyle(redStyle);
	} catch (CustomFunctionException error) {
	    return new ChatComponentText(error.getMessage()).setChatStyle(redStyle);
	} catch (FancyRemainderException errors) {
	    int num1 = (int) (errors.numerator / errors.denominator);
	    double num2 = errors.numerator % errors.denominator;
	    print = new ChatComponentText(num1 + "R");
	    if (num2 % 1 == 0) {
		int i = (int) num2;
		print.appendSibling(new ChatComponentText(String.valueOf(i)));
	    } else {
		print.appendSibling(new ChatComponentText(String.valueOf(num2)));
	    }
	    setLastOutput(sender, num2);
	} catch (AllStandinsUsedException errorsA) {
	    return new ChatComponentTranslation("minecalc.calc.standInsException").setChatStyle(redStyle)
		    .appendSibling(new ChatComponentText(errorsA.getLocalizedMessage()));
	} catch (MultiplePointsException errorsAr) {
	    return new ChatComponentTranslation("minecalc.calc.multiplePointsException").setChatStyle(redStyle);
	} catch (UsageException errorsAre) {
	    return new ChatComponentTranslation("minecalc.calc.usage").setChatStyle(redStyle);
	} catch (InvalidNumberException errorsAreF) {
	    return new ChatComponentTranslation("minecalc.calc.numberFormatException").setChatStyle(redStyle)
		    .appendSibling(new ChatComponentText(errorsAreF.getMessage()));
	} catch (ParenthesisException errorsAreFu) {
	    if (errorsAreFu.tooMany)
		return new ChatComponentTranslation("minecalc.calc.manyParenthesisException").setChatStyle(redStyle);
	    else
		return new ChatComponentTranslation("minecalc.calc.fewParenthesisException").setChatStyle(redStyle);
	} catch (CalcExceptions errorsAreFun) {
	    errorsAreFun.printStackTrace();
	    return new ChatComponentText("Error: An unknown error occured" + (errorsAreFun.getLocalizedMessage() == null
		    ? "" : ". Message: " + errorsAreFun.getLocalizedMessage()));
	}

	// Prepend the arguments to the output, if configured to
	if (MCConfig.returnInput) {
	    print = new ChatComponentText(condensedMath + "=").appendSibling(print);
	}

	return print;
    }

    /**
     * @param sender
     *            Can be null
     * @return The last output the user received
     * @throws PreviousOutputException
     *             If there was no previous output
     */
    private double getLastOutput(ICommandSender sender) throws PreviousOutputException {
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
	    Double last = comp.getLastNumber();
	    return last;
	}
    }

    private void setLastOutput(ICommandSender sender, double newOutput) {
	if (sender == null) {
	    Calculator.consoleLastOutput = newOutput;
	} else {
	    Entity e = sender.getCommandSenderEntity();
	    if (e == null) {
		Calculator.consoleLastOutput = newOutput;
	    } else {
		IMineCalcCompound comp = e.getCapability(MineCalcCompoundProvider.MCC_CAP, null);
		comp.setLastNumber(newOutput);
	    }
	}
    }

    @Override
    public List<String> getCommandAliases() {
	// A list of alternate command names
	List<String> aliases = new ArrayList<String>(Arrays.asList("Calc", "calculate", "Calculate"));
	return aliases;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
	if (args.length % 2 != 1) {
	    ArrayList<String> options = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", "%", "^", "/--"));
	    return options;
	} else
	    return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
	if (sender.getName().equals("@")) {
	    MineCalc.LOGGER.warn("Command blocks cannot use /calc");
	} else {
	    IChatComponent output = calculate(sender, args);
	    output = translateCheck(sender, output);

	    // Send the message back to the user
	    if (sender.getName().equals("Server")) {
		MineCalc.LOGGER.info(output.getUnformattedText());
	    } else {
		EntityPlayer player = (EntityPlayer) sender;
		player.addChatMessage(output);
	    }
	}
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
	return true;
    }

    private IChatComponent translateCheck(ICommandSender sender, IChatComponent output) {
	if (playerHasMod(sender.getCommandSenderEntity()))
	    return output;
	else {
	    Iterator<IChatComponent> it = output.iterator();
	    IChatComponent toReturn = null;
	    while (it.hasNext()) {
		IChatComponent current = it.next();
		if (current instanceof ChatComponentText) {
		    if (toReturn == null) {
			toReturn = new ChatComponentText(current.getUnformattedText())
				.setChatStyle(current.getChatStyle());
		    } else {
			toReturn.appendSibling(new ChatComponentText(current.getUnformattedText())
				.setChatStyle(current.getChatStyle()));
		    }
		} else {
		    ChatComponentTranslation tran = (ChatComponentTranslation) current;
		    String text = I18n.format(tran.getKey());
		    if (toReturn == null) {
			toReturn = new ChatComponentText(text).setChatStyle(tran.getChatStyle());
		    } else {
			toReturn.appendSibling(new ChatComponentText(text).setChatStyle(tran.getChatStyle()));
		    }
		}
	    }

	    return toReturn;
	}
    }

    private boolean playerHasMod(Entity ent) {
	if (ClientProxy.isClientSide())
	    return true;
	if (ent == null)
	    return true;
	return ent.getCapability(MineCalcCompoundProvider.MCC_CAP, null).getHasMineCalc();
    }
}