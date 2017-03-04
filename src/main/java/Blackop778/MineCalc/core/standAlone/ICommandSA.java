package Blackop778.MineCalc.core.standAlone;

import java.util.List;

public interface ICommandSA {
    public String getUsage();

    public String getTrigger();

    /**
     * 
     * @return Don't return null!
     */
    public List<String> getAliases();

    /**
     * 
     * @param arguments
     *            First String shouldn't be the word that indicates this command
     * @return
     */
    public String execute(String[] arguments);
}
