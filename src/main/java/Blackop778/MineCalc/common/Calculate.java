package Blackop778.MineCalc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Blackop778.MineCalc.MineCalc;
import Blackop778.MineCalc.common.CalcExceptions.AllStandinsUsedException;
import Blackop778.MineCalc.common.CalcExceptions.CustomFunctionException;
import Blackop778.MineCalc.common.CalcExceptions.DivisionException;
import Blackop778.MineCalc.common.CalcExceptions.FancyRemainderException;
import Blackop778.MineCalc.common.CalcExceptions.ImaginaryNumberException;
import Blackop778.MineCalc.common.CalcExceptions.MultiplePointsException;
import Blackop778.MineCalc.common.CalcExceptions.OperatorException;
import Blackop778.MineCalc.common.CalcExceptions.PreviousOutputException;
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

    static HashMap<String, Double> lastMap = new HashMap<String, Double>();
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
	try {
	    answer = Calculator.evaluate("", useOOPS, sender);
	    if (answer % 1 == 0) {
		int i = (int) answer;
		print = new TextComponentString(String.valueOf(i));
	    } else {
		print = new TextComponentString(String.valueOf(answer));
	    }
	} catch (NumberFormatException e) {
	    if (e.getMessage().equals("multiple points"))
		return new TextComponentTranslation("minecalc.calc.multiplePointsException").setStyle(redStyle);
	    return new TextComponentTranslation("minecalc.calc.numberFormatException").setStyle(redStyle)
		    .appendSibling(new TextComponentString(e.getMessage().substring(17, e.getMessage().length())));
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

	} catch (FancyRemainderException errorsA) {

	} catch (AllStandinsUsedException errorsAr) {

	} catch (MultiplePointsException errorsAre) {

	} catch (CalcExceptions e) {
	    e.printStackTrace();
	}

	// Prepend the arguments to the output, if configured to
	if (MCConfig.returnInput) {
	    String tempPrint;
	    tempPrint = args[0];
	    for (int i = 1; i < args.length; i++) {
		tempPrint = tempPrint + " " + args[i];
	    }
	    tempPrint = tempPrint + " = ";
	    print = new TextComponentString(tempPrint).appendSibling(print);
	}

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
	    ArrayList<String> options = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", "%", "^", "/-"));
	    return options;
	} else
	    return null;
    }

    public double getDouble(ICommandSender sender, String[] args, int i)
	    throws NumberFormatException, PreviousOutputException {
	if (args[i].equalsIgnoreCase("pi"))
	    return Math.PI;
	else if (args[i].equalsIgnoreCase("l")) {
	    if (lastMap.containsKey(sender.getName()))
		return lastMap.get(sender.getName());
	    else
		throw new PreviousOutputException();
	} else
	    return Double.valueOf(args[i]);
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

    public static boolean isNumber(Character character, boolean lastIsNum, Character lastChar) {
	if (!character.equals('.')) {
	    if (!character.toString().equalsIgnoreCase("l")) {
		if (!character.toString().equalsIgnoreCase("p")) {
		    if (!character.toString().equalsIgnoreCase("i")) {
			if (!(character.equals('-') && (!lastIsNum && !lastChar.toString().equals("/")))) {
			    try {
				Double.valueOf(String.valueOf(character));
			    } catch (NumberFormatException e) {
				return false;
			    }
			}
		    }
		}
	    }
	}

	return true;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
	return true;
    }
}
