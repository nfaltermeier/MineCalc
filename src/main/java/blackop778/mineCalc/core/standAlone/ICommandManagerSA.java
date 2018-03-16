package blackop778.mineCalc.core.standAlone;

import javax.annotation.Nonnull;

import java.util.List;

public interface ICommandManagerSA {
    void add(ICommandSA toAdd);

    ICommandSA get(int index);

    @Nonnull
    List<ICommandSA> getWhole();

    /**
     * Should remove the first argument before passing to an ICommand
     *
     * @param args What arguments the user entered, with splitting on spaces into array elements
     * @return The result of the arguments matched to a command
     */
    String processInput(String[] args);
}
