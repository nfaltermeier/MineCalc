package Blackop778.MineCalc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Blackop778.MineCalc.MineCalc;
import Blackop778.MineCalc.common.CalcExceptions.AllStandinsUsedException;
import Blackop778.MineCalc.common.CalcExceptions.CustomFunctionException;
import Blackop778.MineCalc.common.CalcExceptions.DivisionException;
import Blackop778.MineCalc.common.CalcExceptions.FancyRemainderException;
import Blackop778.MineCalc.common.CalcExceptions.ImaginaryNumberException;
import Blackop778.MineCalc.common.CalcExceptions.InvalidNumberException;
import Blackop778.MineCalc.common.CalcExceptions.MultiplePointsException;
import Blackop778.MineCalc.common.CalcExceptions.OperatorException;
import Blackop778.MineCalc.common.CalcExceptions.PreviousOutputException;
import Blackop778.MineCalc.common.CalcExceptions.UsageException;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class Calculate extends CommandBase {

    public static final Style redStyle = new Style().setColor(TextFormatting.RED);;

    @Override
    public String getCommandName() {
	// What must be typed in following the / to trigger the command
	return "calc";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
	// What is shown when "/help Calculate" is typed in
	return I18n.translateToLocal("minecalc.calc.help");
    }

    public ITextComponent calculate(MinecraftServer server, ICommandSender sender, String[] args) {
	ITextComponent print = null;
	boolean useOOPS;
	double answer;

	useOOPS = Boolean.valueOf(args[0]);
	if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")) {
	    args[0] = "";
	}
	String condensedMath = "";
	for (String s : args) {
	    condensedMath += s;
	}
	try {
	    answer = Calculator.evaluate(condensedMath, useOOPS, sender);
	    Calculator.lastMap.put(sender.getName(), answer);
	    if (answer % 1 == 0) {
		int i = (int) answer;
		print = new TextComponentString(String.valueOf(i));
	    } else {
		print = new TextComponentString(String.valueOf(answer));
	    }
	} catch (ImaginaryNumberException er) {
	    return new TextComponentTranslation("minecalc.calc.imaginaryException").setStyle(redStyle);
	} catch (DivisionException err) {
	    return new TextComponentTranslation("minecalc.calc.divZeroException").setStyle(redStyle);
	} catch (OperatorException erro) {
	    return new TextComponentTranslation("minecalc.calc.symbolException").setStyle(redStyle)
		    .appendSibling(new TextComponentString(" %"))
		    .appendSibling(new TextComponentTranslation("minecalc.calc.symbolExceptionPartTwo"));
	} catch (PreviousOutputException error) {
	    return new TextComponentTranslation("minecalc.calc.previousOutputException").setStyle(redStyle);
	} catch (CustomFunctionException errors) {
	    return new TextComponentString(errors.getMessage()).setStyle(redStyle);
	} catch (FancyRemainderException errorsA) {
	    int num1 = (int) (errorsA.numerator / errorsA.denominator);
	    double num2 = errorsA.numerator % errorsA.denominator;
	    print = new TextComponentString(num1 + "R" + num2);
	    Calculator.lastMap.put(sender.getName(), num2);
	} catch (AllStandinsUsedException errorsAr) {
	    return new TextComponentTranslation("minecalc.calc.standInsException").setStyle(redStyle)
		    .appendSibling(new TextComponentString(errorsAr.getMessage()));
	} catch (MultiplePointsException errorsAre) {
	    return new TextComponentTranslation("minecalc.calc.multiplePointsException").setStyle(redStyle);
	} catch (UsageException errorsAreF) {
	    return new TextComponentTranslation("minecalc.calc.usage").setStyle(redStyle);
	} catch (InvalidNumberException errorsAreFu) {
	    return new TextComponentTranslation("minecalc.calc.numberFormatException").setStyle(redStyle);
	} catch (CalcExceptions e) {
	    e.printStackTrace();
	}

	// Prepend the arguments to the output, if configured to
	if (MCConfig.returnInput)
	    print = new TextComponentString(condensedMath + "=").appendSibling(print);

	return print;
    }

    @Override
    public List<String> getCommandAliases() {
	// A list of alternate command names
	List<String> aliases = new ArrayList<String>(Arrays.asList("Calc", "calculate", "Calculate"));
	return aliases;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
	    BlockPos pos) {
	if (args.length % 2 != 1) {
	    ArrayList<String> options = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", "%", "^", "/--"));
	    return options;
	} else
	    return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
	if (sender.getName().equals("@")) {
	    MineCalc.Logger.warn("Command blocks cannot use /calc");
	} else {
	    ITextComponent output = calculate(server, sender, args);

	    // Send the message back to the user
	    if (sender.getName().equals("Server")) {
		MineCalc.Logger.info(output.getUnformattedText());
	    } else {
		EntityPlayer player = (EntityPlayer) sender;
		player.addChatMessage(output);
	    }
	}
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
	return true;
    }
}
