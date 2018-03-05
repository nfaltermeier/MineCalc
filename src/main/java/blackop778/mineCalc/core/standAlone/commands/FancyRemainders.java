package blackop778.mineCalc.core.standAlone.commands;

import blackop778.mineCalc.core.standAlone.ICommandSA;

import java.util.ArrayList;
import java.util.List;

public class FancyRemainders implements ICommandSA {

    private static boolean fancyRemainders = true;

    @Override
    public String getUsage() {
        return "fancyRemainders <boolean>";
    }

    @Override
    public String getTrigger() {
        return "fancyRemainders";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<String>();
    }

    @Override
    public String execute(String[] arguments) {
        if (arguments.length == 0)
            return "Remainders will " + (fancyRemainders ? "" : "not ") + "be fancy";
        else if (arguments[0].equalsIgnoreCase("true") || arguments[0].equalsIgnoreCase("false")) {
            fancyRemainders = Boolean.valueOf(arguments[0]);
            return "Remainders will " + (fancyRemainders ? "" : "not ") + "be fancy";
        } else
            return "Usage: " + getUsage();
    }

    @Override
    public String getEffect() {
        return "Determines whether or not 'calc 7%5' will return '1R2' or simply '2'";
    }

    public static boolean getFancyRemainder() {
        return fancyRemainders;
    }

}
