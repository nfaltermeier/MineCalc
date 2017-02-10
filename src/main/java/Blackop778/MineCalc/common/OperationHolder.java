package Blackop778.MineCalc.common;

public class OperationHolder {
    private IOperation[][] operations;

    public OperationHolder() {
	operations = new IOperation[6][0];
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
