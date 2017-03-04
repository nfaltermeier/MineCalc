package Blackop778.MineCalc.core.standAlone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Blackop778.MineCalc.common.MCConfig;
import Blackop778.MineCalc.core.CalcExceptions;
import Blackop778.MineCalc.core.CalcExceptions.AllStandinsUsedException;
import Blackop778.MineCalc.core.CalcExceptions.CustomFunctionException;
import Blackop778.MineCalc.core.CalcExceptions.DivisionException;
import Blackop778.MineCalc.core.CalcExceptions.FancyRemainderException;
import Blackop778.MineCalc.core.CalcExceptions.ImaginaryNumberException;
import Blackop778.MineCalc.core.CalcExceptions.InvalidNumberException;
import Blackop778.MineCalc.core.CalcExceptions.MultiplePointsException;
import Blackop778.MineCalc.core.CalcExceptions.OperatorException;
import Blackop778.MineCalc.core.CalcExceptions.ParenthesisException;
import Blackop778.MineCalc.core.CalcExceptions.PreviousOutputException;
import Blackop778.MineCalc.core.CalcExceptions.UsageException;
import Blackop778.MineCalc.core.Calculator;

public class CalculateSA implements ICommandSA {

    @Override
    public String getUsage() {
	return "calc [number][operator][number]<operator><number>";
    }

    @Override
    public String getTrigger() {
	return "calc";
    }

    @Override
    public List<String> getAliases() {
	List<String> aliases = new ArrayList<String>(Arrays.asList("Calc", "calculate", "Calculate"));
	return aliases;
    }

    @Override
    public String execute(String[] args) {
	String print;
	boolean useOOPS;

	if (args.length == 0)
	    return "Usage: " + getUsage();
	useOOPS = Boolean.valueOf(args[0]);
	if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")) {
	    args[0] = "";
	}
	String condensedMath = "";
	for (String s : args) {
	    condensedMath += s;
	}
	try {
	    double answer = Calculator.evaluate(condensedMath, useOOPS, Calculator.consoleLastOutput);
	    Calculator.consoleLastOutput = answer;
	    if (answer % 1 == 0) {
		int i = (int) answer;
		print = String.valueOf(i);
	    } else {
		print = String.valueOf(answer);
	    }
	} catch (ImaginaryNumberException e) {
	    return "Error: Imaginary numbers are not supported";
	} catch (DivisionException er) {
	    return "Error: Cannot divide by 0";
	} catch (OperatorException err) {
	    return "Error: Valid symbols are '+, -, *, /, %, ^, /--'";
	} catch (PreviousOutputException erro) {
	    return "Error: There is no previous output to insert";
	} catch (CustomFunctionException error) {
	    return error.getMessage();
	} catch (FancyRemainderException errors) {
	    int num1 = (int) (errors.numerator / errors.denominator);
	    double num2 = errors.numerator % errors.denominator;
	    print = num1 + "R";
	    if (num2 % 1 == 0) {
		int i = (int) num2;
		print += String.valueOf(i);
	    } else {
		print += String.valueOf(num2);
	    }
	    Calculator.consoleLastOutput = num2;
	} catch (AllStandinsUsedException errorsA) {
	    return "Error: Please exclude one of the following symbols:" + errorsA.getMessage();
	} catch (MultiplePointsException errorsAr) {
	    return "Error: A number may only have one decimal point";
	} catch (UsageException errorsAre) {
	    return "Usage: " + getUsage();
	} catch (InvalidNumberException errorsAreF) {
	    return "Error: One of the inputed numbers could not be interpreted as a number";
	} catch (ParenthesisException errorsAreFu) {
	    if (errorsAreFu.tooMany)
		return "Error: Too many opening parenthesis have been input. Some may not have been closed";
	    else
		return "Error: Too many closing parenthesis have been input. Some may not have been opened";
	} catch (CalcExceptions errorsAreFun) {
	    errorsAreFun.printStackTrace();
	    return "Error: An unknown error occured" + (errorsAreFun.getLocalizedMessage() == null ? ""
		    : ". Message: " + errorsAreFun.getLocalizedMessage());
	}

	// Prepend the arguments to the output, if configured to
	if (MCConfig.returnInput) {
	    print = condensedMath + "=" + print;
	}

	return print;
    }

}
