package blackop778.mineCalc.core.standAlone;

import java.util.List;

public interface ICommandSA {
    /**
     * @return Arguments for command
     */
    public String getUsage();

    /**
     * @return word that signals this command
     */
    public String getTrigger();

    /**
     * @return Don't return null!
     */
    public List<String> getAliases();

    /**
     * @param arguments First String shouldn't be the word that indicates this command
     * @return
     */
    public String execute(String[] arguments);

    /**
     * Gets what the command does
     *
     * @return
     */
    public String getEffect();
}
