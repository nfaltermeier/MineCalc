package blackop778.mineCalc.core.standAlone;

import java.util.List;

public interface ICommandSA {
    /**
     * @return Arguments for command
     */
    String getUsage();

    /**
     * @return word that signals this command
     */
    String getTrigger();

    /**
     * @return Don't return null!
     */
    List<String> getAliases();

    /**
     * @param arguments First String shouldn't be the word that indicates this command
     * @return The result of the arguments processed through the command
     */
    String execute(String[] arguments);

    /**
     * @return A description of what the command does
     */
    String getEffect();
}
