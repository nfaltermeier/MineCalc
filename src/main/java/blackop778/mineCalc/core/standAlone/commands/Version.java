package blackop778.mineCalc.core.standAlone.commands;

import blackop778.mineCalc.core.standAlone.ICommandSA;
import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class Version implements ICommandSA {

    public static final String version = "4.0.0";

    @Nonnull
    @Override
    public String getUsage() {
        return "version";
    }

    @Nonnull
    @Override
    public String getTrigger() {
        return "version";
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        return new ArrayList<String>();
    }

    @Nonnull
    @Override
    public String execute(String[] arguments) {
        return version;
    }

    @Nonnull
    @Override
    public String getEffect() {
        return "Returns the version of MineCalc running";
    }

}
