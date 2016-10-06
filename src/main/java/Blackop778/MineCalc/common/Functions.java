package Blackop778.MineCalc.common;

import Blackop778.MineCalc.MineCalc;
import Blackop778.MineCalc.common.ArgumentManager.Type;
import Blackop778.MineCalc.common.CalcExceptions.DivisionException;
import Blackop778.MineCalc.common.CalcExceptions.ImaginaryNumberException;

public abstract class Functions {
    static void addFunctions() {
	MineCalc.functions.add(new IFunction() {
	    @Override
	    public Type getType(Character currentChar, Type lastType) {
		if (currentChar.equals('+'))
		    return Type.ADDITION;
		return Type.JUNK;
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		return n1 + n2;
	    }

	    @Override
	    public Type getHandledType() {
		return Type.ADDITION;
	    }
	});

	MineCalc.functions.add(new IFunction() {
	    @Override
	    public Type getType(Character currentChar, Type lastType) {
		if (currentChar.equals('-')) {
		    if (lastType.equals(Type.DIVISION))
			return Type.ROOT;
		    return Type.DIVISION;
		}

		return Type.JUNK;
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		return n1 - n2;
	    }

	    @Override
	    public Type getHandledType() {
		return Type.SUBTRACTION;
	    }
	});

	MineCalc.functions.add(new IFunction() {
	    @Override
	    public Type getType(Character currentChar, Type lastType) {
		if (currentChar.equals('*') || currentChar.toString().equalsIgnoreCase("x"))
		    return Type.MULTIPLICATION;
		return Type.JUNK;
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		return n1 * n2;
	    }

	    @Override
	    public Type getHandledType() {
		return Type.MULTIPLICATION;
	    }
	});

	MineCalc.functions.add(new IFunction() {
	    @Override
	    public Type getType(Character currentChar, Type lastType) {
		if (currentChar.equals('/'))
		    return Type.DIVISION;
		return Type.JUNK;
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		if (n2 == 0)
		    throw new DivisionException();
		return n1 / n2;
	    }

	    @Override
	    public Type getHandledType() {
		return Type.DIVISION;
	    }
	});

	MineCalc.functions.add(new IFunction() {
	    @Override
	    public Type getType(Character currentChar, Type lastType) {
		if (currentChar.equals('%'))
		    return Type.MODULO;
		return Type.JUNK;
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		if (n2 == 0)
		    throw new DivisionException();
		return n1 % n2;
	    }

	    @Override
	    public Type getHandledType() {
		return Type.MODULO;
	    }
	});

	MineCalc.functions.add(new IFunction() {
	    @Override
	    public Type getType(Character currentChar, Type lastType) {
		if (currentChar.equals('^'))
		    return Type.EXPONENT;
		return Type.JUNK;
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		if (n1 < 0) {
		    double num = 1 / n2;
		    if (num % 2 == 0)
			throw new ImaginaryNumberException();
		}
		return Math.pow(n1, n2);
	    }

	    @Override
	    public Type getHandledType() {
		return Type.EXPONENT;
	    }
	});

	MineCalc.functions.add(new IFunction() {

	    @Override
	    public Type getType(Character currentChar, Type lastType) {
		if (currentChar.equals('-') && lastType.equals(Type.DIVISION))
		    return Type.ROOT;
		return Type.JUNK;
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		if (n1 < 0 && n2 % 2 == 0)
		    throw new ImaginaryNumberException();
		return Math.pow(n1, 1 / n2);
	    }

	    @Override
	    public Type getHandledType() {
		return Type.ROOT;
	    }

	});
    }
}
