package Blackop778.MineCalc.core.standAlone;

import java.util.List;

public interface ICommand {
    public String getUsage();

    public String getTrigger();

    /**
     * 
     * @return Don't return null!
     */
    public List<String> getAliases();

    public String execute(String input);
}
