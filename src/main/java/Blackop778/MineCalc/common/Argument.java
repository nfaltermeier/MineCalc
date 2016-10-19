package Blackop778.MineCalc.common;

import java.util.ArrayList;
import java.util.Comparator;

import Blackop778.MineCalc.common.CalcExceptions.RecursiveLoopException;

public class Argument implements Comparator<Argument> {
    public static final int MAXLOOPS = 100;

    public final int index;
    public final double importance;
    private String firstNumber;
    private String operator;
    private String secondNumber;

    protected Argument(int index, double importance, String contents) {
	this.index = index;
	this.importance = importance;
	int startIndex = 0;
	boolean lastNumber = true;
	int inCount = 0;
	for (int i = 0; i <= contents.length(); i++) {
	    if (i == contents.length()) {
		secondNumber = contents.substring(startIndex, i);
	    } else {
		boolean number;
		if (i == 0) {
		    number = isNumber(contents.charAt(i), lastNumber, '7');
		} else {
		    number = isNumber(contents.charAt(i), lastNumber, contents.charAt(i - 1));
		}
		if (number != lastNumber) {
		    if (inCount == 0) {
			firstNumber = contents.substring(startIndex, i);
		    } else if (inCount == 1) {
			operator = contents.substring(startIndex, i);
		    }
		    startIndex = i;
		    inCount++;
		    lastNumber = number;
		}
	    }
	}
    }

    private boolean isNumber(Character character, boolean lastIsNum, Character lastChar) {
	try {
	    Double.valueOf(String.valueOf(character));
	} catch (NumberFormatException e) {
	    if (character.equals('.') || character.toString().equalsIgnoreCase("l")
		    || character.toString().equalsIgnoreCase("p") || character.toString().equalsIgnoreCase("i")
		    || (character.equals('-') && (!lastIsNum && !lastChar.toString().equals("/")))
		    || character.equals('$') || (character.equals('#') && lastChar.equals('$')))
		return true;
	    else
		return false;
	}
	return true;
    }

    public String getFirstNumber(ArrayList<Argument> argumentList) throws RecursiveLoopException {
	return getFirstNumber(argumentList, 1);
    }

    public String getFirstNumber(ArrayList<Argument> argumentList, int loopCount) throws RecursiveLoopException {
	if (!firstNumber.contains("$"))
	    return firstNumber;
	if (loopCount == MAXLOOPS)
	    throw new RecursiveLoopException();
	String numberS = firstNumber.replaceAll("\\$", "");
	int number = Integer.valueOf(numberS.split("#")[1]);

	return argumentList.get(number).getFirstNumber(argumentList, loopCount++);
    }

    public String getOperator() {
	return operator;
    }

    public String getSecondNumber(ArrayList<Argument> argumentList) throws RecursiveLoopException {
	return getSecondNumber(argumentList, 1);
    }

    public String getSecondNumber(ArrayList<Argument> argumentList, int loopCount) throws RecursiveLoopException {
	if (!secondNumber.contains("$"))
	    return secondNumber;
	if (loopCount == MAXLOOPS)
	    throw new RecursiveLoopException();
	String numberS = secondNumber.replaceAll("\\$", "");
	int number = Integer.valueOf(numberS.split("#")[1]);

	return argumentList.get(number).getSecondNumber(argumentList, loopCount++);
    }

    @Override
    public int compare(Argument o1, Argument o2) {
	int importanceOrder = new Double(o1.importance).compareTo(o2.importance);
	if (importanceOrder != 0)
	    return importanceOrder;
	return -(new Integer(o1.index).compareTo(o2.index));

    }
}
