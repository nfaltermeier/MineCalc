package Blackop778.MineCalc.core;

import Blackop778.MineCalc.core.CalcExceptions.DivisionException;
import Blackop778.MineCalc.core.CalcExceptions.ImaginaryNumberException;
import Blackop778.MineCalc.core.CalcExceptions.InvalidNumberException;
import Blackop778.MineCalc.core.CalcExceptions.MultiplePointsException;
import Blackop778.MineCalc.core.CalcExceptions.OperatorException;
import Blackop778.MineCalc.core.CalcExceptions.ParenthesisException;
import Blackop778.MineCalc.core.CalcExceptions.PreviousOutputException;
import Blackop778.MineCalc.core.CalcExceptions.UsageException;

public abstract class ArgumentManagerTest {
    public static boolean debug = false;

    public static void main(String[] args) {
	Operations.addOperations();
	diagnostic();
    }

    public static void diagnostic() {
	String[] questions = { "3+(4+3)*6", "(6*6)/--2+((-1*1)+1+(1*-1))*3", "2+3*4", "2+3*4", "1.25*4/2",
		"5*(1+1-(6/2))", "5--6", "5y6", "80/0", "-5/--2", "6*l", "2^3^2", "3^(2+1)", "0/5", "2^-2",
		"(1/2)^(1+1)", "-8^(1/2)", "-8^(1/3)", "9/---2", ".25+6..75", "8-9.0.4", "8*", "r%4", "pi*1", "e*1",
		"1+(6*7", "(9*8)", "((8*8))-3", "7*(5%3(", "1 + 1 + 1 - 1" };
	boolean[] useOOPSs = { true, true, true, false, true, true, false, false, true, true, true, true, true, true,
		true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true };
	Object[] answers = { 45.0, 3.0, 14.0, 20.0, 2.5, -5.0, 11.0, new OperatorException(), new DivisionException(),
		new ImaginaryNumberException(), new PreviousOutputException(), 64.0, 27.0, 0.0, .25, .25,
		new ImaginaryNumberException(), -2.0, (1 / 3.0), new MultiplePointsException(),
		new MultiplePointsException(), new UsageException(), new InvalidNumberException(), Math.PI, Math.E,
		new ParenthesisException(true), 72.0, 61.0, new ParenthesisException(true), 2.0 };
	int fails = 0;
	for (int i = 0; i < questions.length; i++) {
	    if (!testMath(questions[i], useOOPSs[i], answers[i], i)) {
		fails++;
	    }
	}

	if (fails == 0) {
	    System.out.println("All tests succeeded");
	} else {
	    System.out.println(fails + " test" + (fails == 1 ? "" : "s") + " failed");
	}
    }

    public static boolean testMath(String math, boolean useOOPS, Object answer, int index) {
	Object result;
	try {
	    result = Calculator.evaluate(math, useOOPS, null);
	} catch (CalcExceptions e) {
	    result = e;
	}

	boolean success;
	if (result instanceof CalcExceptions) {
	    success = result.getClass().equals(answer.getClass());
	} else {
	    success = result.equals(answer);
	}

	if (!success || debug) {
	    System.out.println("Test " + (index + 1) + " result: " + (success ? "Success" : "Failure") + ". " + math
		    + " Returned: " + result + " Expected: " + answer);
	}

	return success;
    }
}
