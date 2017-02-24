package Blackop778.MineCalc.common;

import Blackop778.MineCalc.common.CalcExceptions.DivisionException;
import Blackop778.MineCalc.common.CalcExceptions.ImaginaryNumberException;

public abstract class Operations {
    static void addOperations() {
	Calculator.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "+" };
	    }

	    @Override
	    public double evaluateFunction(double num1, double num2) throws CalcExceptions {
		return num1 + num2;
	    }

	    @Override
	    public int getImportance() {
		return 2;
	    }
	});

	Calculator.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "-" };
	    }

	    @Override
	    public double evaluateFunction(double num1, double num2) throws CalcExceptions {
		return num1 - num2;
	    }

	    @Override
	    public int getImportance() {
		return 2;
	    }
	});

	Calculator.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "*", "x", "X" };
	    }

	    @Override
	    public double evaluateFunction(double num1, double num2) throws CalcExceptions {
		return num1 * num2;
	    }

	    @Override
	    public int getImportance() {
		return 4;
	    }
	});

	Calculator.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "/" };
	    }

	    @Override
	    public double evaluateFunction(double num1, double num2) throws CalcExceptions {
		if (num2 == 0)
		    throw new DivisionException();
		return num1 / num2;
	    }

	    @Override
	    public int getImportance() {
		return 4;
	    }
	});

	Calculator.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "%" };
	    }

	    @Override
	    public double evaluateFunction(double num1, double num2) throws CalcExceptions {
		if (num2 == 0)
		    throw new DivisionException();
		return num1 % num2;
	    }

	    @Override
	    public int getImportance() {
		return 4;
	    }
	});

	Calculator.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "^" };
	    }

	    @Override
	    public double evaluateFunction(double base, double num2) throws CalcExceptions {
		boolean negative = false;
		if (base < 0) {
		    double num3 = 1 / num2;
		    if (num3 % 2 == 0)
			throw new ImaginaryNumberException();
		    else {
			base = -base;
			negative = true;
		    }
		}
		double answer = Math.pow(base, num2);
		return negative ? -answer : answer;
	    }

	    @Override
	    public int getImportance() {
		return 6;
	    }
	});

	Calculator.operations.add(new IOperation() {

	    @Override
	    public String[] getOperators() {
		return new String[] { "/--" };
	    }

	    @Override
	    public double evaluateFunction(double base, double num2) throws CalcExceptions {
		boolean negative = false;
		if (base < 0)
		    if (num2 % 2 == 0)
			throw new ImaginaryNumberException();
		    else {
			base = -base;
		    }
		double answer = Math.pow(base, 1 / num2);
		return negative ? -answer : answer;
	    }

	    @Override
	    public int getImportance() {
		return 6;
	    }
	});
    }
}
