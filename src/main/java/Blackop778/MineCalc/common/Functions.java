package Blackop778.MineCalc.common;

import Blackop778.MineCalc.MineCalc;
import Blackop778.MineCalc.common.ArgumentManager.FunctionType;
import Blackop778.MineCalc.common.ArgumentManager.Type;
import Blackop778.MineCalc.common.CalcExceptions.DivisionException;
import Blackop778.MineCalc.common.CalcExceptions.ImaginaryNumberException;

public abstract class Functions {
    static void addFunctions() {
	MineCalc.functions.add(new IFunction() {
	    @Override
	    public FunctionType getType(Character currentChar, Character lastCharacter, Type lastType) {
		if (currentChar.equals('+'))
		    return new FunctionType(this, Type.ADDITION);
		return new FunctionType(null, Type.JUNK);
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		return n1 + n2;
	    }
	});

	MineCalc.functions.add(new IFunction() {
	    @Override
	    public FunctionType getType(Character currentChar, Character lastCharacter, Type lastType) {
		if (currentChar.equals('-'))
		    return new FunctionType(this, Type.SUBTRACTION);

		return new FunctionType(null, Type.JUNK);
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		return n1 - n2;
	    }
	});

	MineCalc.functions.add(new IFunction() {
	    @Override
	    public FunctionType getType(Character currentChar, Character lastCharacter, Type lastType) {
		if (currentChar.equals('*') || currentChar.toString().equalsIgnoreCase("x"))
		    return new FunctionType(this, Type.MULTIPLICATION);
		return new FunctionType(null, Type.JUNK);
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		return n1 * n2;
	    }
	});

	MineCalc.functions.add(new IFunction() {
	    @Override
	    public FunctionType getType(Character currentChar, Character lastCharacter, Type lastType) {
		if (currentChar.equals('/'))
		    return new FunctionType(this, Type.DIVISION);
		return new FunctionType(null, Type.JUNK);
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		if (n2 == 0)
		    throw new DivisionException();
		return n1 / n2;
	    }
	});

	MineCalc.functions.add(new IFunction() {
	    @Override
	    public FunctionType getType(Character currentChar, Character lastCharacter, Type lastType) {
		if (currentChar.equals('%'))
		    return new FunctionType(this, Type.MODULO);
		return new FunctionType(null, Type.JUNK);
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		if (n2 == 0)
		    throw new DivisionException();
		return n1 % n2;
	    }
	});

	MineCalc.functions.add(new IFunction() {
	    @Override
	    public FunctionType getType(Character currentChar, Character lastCharacter, Type lastType) {
		if (currentChar.equals('^'))
		    return new FunctionType(this, Type.EXPONENT);
		return new FunctionType(null, Type.JUNK);
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
	});

	MineCalc.functions.add(new IFunction() {

	    @Override
	    public FunctionType getType(Character currentChar, Character lastCharacter, Type lastType) {
		if (currentChar.equals('-') && lastCharacter.equals('/'))
		    return new FunctionType(this, Type.ROOT);
		return new FunctionType(null, Type.JUNK);
	    }

	    @Override
	    public double evaluateFunction(double n1, double n2) throws CalcExceptions {
		if (n1 < 0 && n2 % 2 == 0)
		    throw new ImaginaryNumberException();
		return Math.pow(n1, 1 / n2);
	    }
	});
    }
}
