package Blackop778.MineCalc.core.standAlone.commands;

import java.util.ArrayList;
import java.util.List;

import Blackop778.MineCalc.core.standAlone.ICommandSA;

public class ReturnInput implements ICommandSA {

    private static boolean returnInput = true;

    @Override
    public String getUsage() {
	return "returnInput <boolean>";
    }

    @Override
    public String getTrigger() {
	return "returnInput";
    }

    @Override
    public List<String> getAliases() {
	return new ArrayList<String>();
    }

    @Override
    public String execute(String[] arguments) {
	if (arguments.length == 0)
	    return "Input will " + (returnInput ? "" : "not ") + "be returned";
	else if (arguments[0].equalsIgnoreCase("true") || arguments[0].equalsIgnoreCase("false")) {
	    returnInput = Boolean.valueOf(arguments[0]);
	    return "Input will " + (returnInput ? "" : "not ") + "be returned";
	} else
	    return "Usage: " + getUsage();
    }

    @Override
    public String getEffect() {
	return "Determines whether or not 'calc' will prepend the input when returning answers";
    }

    public static boolean getReturnInput() {
	return returnInput;
    }
}
