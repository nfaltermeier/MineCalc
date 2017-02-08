package Blackop778.MineCalc.common;

import Blackop778.MineCalc.MineCalc;
import Blackop778.MineCalc.common.CalcExceptions.DivisionException;
import Blackop778.MineCalc.common.CalcExceptions.ImaginaryNumberException;

public abstract class Operations {
    static void addOperations() {
	MineCalc.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "+" };
	    }

	    @Override
	    public double evaluateFunction(int num1, int num2) throws CalcExceptions {
		return num1 + num2;
	    }

	    @Override
	    public int getImportance() {
		return 2;
	    }
	});

	MineCalc.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "-" };
	    }

	    @Override
	    public double evaluateFunction(int num1, int num2) throws CalcExceptions {
		return num1 - num2;
	    }

	    @Override
	    public int getImportance() {
		return 2;
	    }
	});

	MineCalc.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "*", "x", "X" };
	    }

	    @Override
	    public double evaluateFunction(int num1, int num2) throws CalcExceptions {
		return num1 * num2;
	    }

	    @Override
	    public int getImportance() {
		return 4;
	    }
	});

	MineCalc.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "/" };
	    }

	    @Override
	    public double evaluateFunction(int num1, int num2) throws CalcExceptions {
		if (num2 == 0)
		    throw new DivisionException();
		return num1 / num2;
	    }

	    @Override
	    public int getImportance() {
		return 4;
	    }
	});

	MineCalc.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "%" };
	    }

	    @Override
	    public double evaluateFunction(int num1, int num2) throws CalcExceptions {
		if (num2 == 0)
		    throw new DivisionException();
		return num1 % num2;
	    }

	    @Override
	    public int getImportance() {
		return 4;
	    }
	});

	MineCalc.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "^" };
	    }

	    @Override
	    public double evaluateFunction(int num1, int num2) throws CalcExceptions {
		if (num1 < 0) {
		    double num3 = 1 / num2;
		    if (num3 % 2 == 0)
			throw new ImaginaryNumberException();
		}
		return Math.pow(num1, num2);
	    }

	    @Override
	    public int getImportance() {
		return 6;
	    }
	});

	MineCalc.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "/--" };
	    }

	    @Override
	    public double evaluateFunction(int num1, int num2) throws CalcExceptions {
		if (num1 < 0 && num2 % 2 == 0)
		    throw new ImaginaryNumberException();
		return Math.pow(num1, 1 / num2);
	    }

	    @Override
	    public int getImportance() {
		return 6;
	    }
	});
    }
}
