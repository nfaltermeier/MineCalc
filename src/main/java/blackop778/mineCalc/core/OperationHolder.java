package blackop778.mineCalc.core;

import java.util.ArrayList;
import java.util.List;

public class OperationHolder {
    private IOperation[][] operations;
    public final boolean INITIALIZE_WITH_DEFAULTS;
    // 16 - Bitwise NOT
    // 15 - Empty
    // 14 - Powers
    // 13 - Empty
    // 12 - Multiplicative
    // 11 - Empty
    // 10 - Additive
    // 9 - Empty
    // 8 - Shift
    // 7 - Empty
    // 6 - Bitwise AND
    // 5 - Empty
    // 4 - Bitwise XOR
    // 3 - Empty
    // 2 - Bitwise OR
    // 1 - Empty
    public final int LEVELS;

    public OperationHolder(boolean initializeWithDefaults) {
        this(initializeWithDefaults, 16);
    }

    public OperationHolder(boolean initializeWithDefaults, int levels) {
        this.LEVELS = levels;
        this.INITIALIZE_WITH_DEFAULTS = initializeWithDefaults;
        operations = new IOperation[levels][0];
        if (this.INITIALIZE_WITH_DEFAULTS) {
            List<IOperation> temp = Operations.addOperations(new ArrayList<IOperation>());
            for (IOperation op : temp) {
                add(op);
            }
        }
    }

    public void add(IOperation op) {
        int level = op.getImportance();
        operations[level - 1] = expandArray(operations[level - 1]);
        operations[level - 1][operations[level - 1].length - 1] = op;
    }

    public static IOperation[] expandArray(IOperation[] array) {
        IOperation[] newArray = new IOperation[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Returns the importance level specified by level. Should be between or
     * equal to 1 through LEVELS.
     */
    public IOperation[] getLevel(int level) {
        return operations[level - 1];
    }
}
