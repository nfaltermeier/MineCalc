package blackop778.mineCalc.core.standAlone;

import java.util.List;

public interface ICommandManagerSA {
    void add(ICommandSA toAdd);

    ICommandSA get(int index);

    List<ICommandSA> getWhole();

    /**
     * Should remove the first argument before passing to an ICommand
     *
     * @param args
     * @return
     */
    String processInput(String[] args);
}
