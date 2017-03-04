package Blackop778.MineCalc.core.standAlone;

import java.util.List;

public interface ICommandManager {
    public void addCommand(ICommand toAdd);

    public ICommand get(int index);

    public List<ICommand> getWhole();

    /**
     * Should remove the first argument before passing to an ICommand
     * 
     * @param args
     * @return
     */
    public String processInput(String[] args);
}
