package Blackop778.MineCalc.core.standAlone;

import java.util.List;

public interface ICommandManagerSA {
    public void addCommand(ICommandSA toAdd);

    public ICommandSA get(int index);

    public List<ICommandSA> getWhole();

    /**
     * Should remove the first argument before passing to an ICommand
     * 
     * @param args
     * @return
     */
    public String processInput(String[] args);
}
