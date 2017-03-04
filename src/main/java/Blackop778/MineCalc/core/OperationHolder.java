package Blackop778.MineCalc.core;

import java.util.ArrayList;
import java.util.List;

public class OperationHolder {
    private IOperation[][] operations;
    public final boolean initializeWithDefaults;

    public OperationHolder(boolean initializeWithDefaults) {
	this.initializeWithDefaults = initializeWithDefaults;
	operations = new IOperation[6][0];
	if (this.initializeWithDefaults) {
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
     * equal to 1 through 6.
     */
    public IOperation[] getLevel(int level) {
	return operations[level - 1];
    }
}
