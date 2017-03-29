package blackop778.mineCalc.core;

import java.util.List;

import blackop778.mineCalc.core.CalcExceptions.DivisionException;
import blackop778.mineCalc.core.CalcExceptions.ImaginaryNumberException;

public abstract class Operations {

    /**
     * In most cases is automatically called by an OperationHolder, only if it
     * was passed 'false' in the constructor does this need to be called
     * manually
     * 
     * @param toAddTo
     * @return
     */
    public static List<IOperation> addOperations(List<IOperation> toAddTo) {
	toAddTo.add(new Addition());
	toAddTo.add(new Subtraction());
	toAddTo.add(new Multiplication());
	toAddTo.add(new Division());
	toAddTo.add(new Modulus());
	toAddTo.add(new Exponent());
	toAddTo.add(new Root());
	toAddTo.add(new Sine());
	toAddTo.add(new ArcSine());
	return toAddTo;
    }

    public static class Addition implements IBinaryOperation {
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
    }

    public static class Subtraction implements IBinaryOperation {
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
    }

    public static class Multiplication implements IBinaryOperation {
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
    }

    public static class Division implements IBinaryOperation {
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
    }

    public static class Modulus implements IBinaryOperation {
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
    }

    public static class Exponent implements IBinaryOperation {
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
    }

    public static class Root extends Exponent {
	@Override
	public String[] getOperators() {
	    return new String[] { "/--" };
	}

	@Override
	public double evaluateFunction(double base, double num2) throws CalcExceptions {
	    return super.evaluateFunction(base, 1 / num2);
	}
    }

    public static class Sine implements IUnaryOperation {

	@Override
	public String[] getOperators() {
	    return new String[] { "sin", "sine" };
	}

	@Override
	public int getImportance() {
	    return 8;
	}

	@Override
	public double evaluateFunction(double input) {
	    double answer = Math.sin(AngleManager.convertInputToRadian(input));
	    // FLOAAATTTT!
	    double tAnswer = Math.abs(answer);
	    if (tAnswer < .000000001 && tAnswer > 0)
		answer = 0;
	    return answer;
	}

    }

    public static class ArcSine implements IUnaryOperation {

	@Override
	public String[] getOperators() {
	    return new String[] { "asin", "asine", "arcsin", "arcsine" };
	}

	@Override
	public int getImportance() {
	    return 8;
	}

	@Override
	public double evaluateFunction(double input) {
	    double answer = AngleManager.convertRadianToInput(Math.asin(input));
	    // FLOAAATTTT!
	    double tAnswer = Math.abs(answer);
	    if (tAnswer < .000000001 && tAnswer > 0)
		answer = 0;
	    return answer;
	}

    }
}
