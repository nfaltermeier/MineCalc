package Blackop778.MineCalc.common;

import java.util.ArrayList;

import Blackop778.MineCalc.common.CalcExceptions.RecursiveLoopException;

public class Argument implements Comparable<Argument> {
    public static final int MAXLOOPS = 100;

    public final int index;
    public final int importance;
    private String firstNumber;
    public final IFunction function;
    private String secondNumber;
    // TODO: Remove for release
    public String contents;
    private boolean sealed;

    protected Argument(int index, int importance, String contents, IFunction function) {
	this.index = index;
	this.importance = importance;
	this.function = function;
	this.contents = contents;
	this.sealed = false;
	int startIndex = 0;
	boolean lastNumber = false;
	int inCount = -1;
	char lastChar = '$';
	for (int i = 0; i <= contents.length(); i++) {
	    if (i == contents.length()) {
		secondNumber = contents.substring(startIndex, i);
	    } else {
		boolean number;
		number = isNumber(contents.charAt(i), lastNumber, lastChar);
		if (number != lastNumber) {
		    if (inCount == 0) {
			firstNumber = contents.substring(startIndex, i);
		    }
		    startIndex = i;
		    inCount++;
		    lastNumber = number;
		}
		lastChar = contents.charAt(i);
	    }
	}
    }

    private boolean isNumber(Character character, boolean lastIsNum, Character lastChar) {
	try {
	    Double.valueOf(String.valueOf(character));
	} catch (NumberFormatException e) {
	    if (character.equals('.') || character.toString().equalsIgnoreCase("l")
		    || character.toString().equalsIgnoreCase("p") || character.toString().equalsIgnoreCase("i")
		    || (character.equals('-') && (!lastIsNum && !lastChar.equals('/'))
			    || character.equals(ArgumentManager.referenceIndicator)
			    || (character.equals(ArgumentManager.referenceIndexIndicator)
				    && lastChar.equals(ArgumentManager.referenceIndicator))))
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
	if (!firstNumber.contains(String.valueOf(ArgumentManager.referenceIndicator)))
	    return firstNumber;
	if (loopCount == MAXLOOPS)
	    throw new RecursiveLoopException();
	String numberS = firstNumber.replaceAll("\\" + ArgumentManager.referenceIndicator, "");
	int number = Integer.valueOf(numberS.split(String.valueOf(ArgumentManager.referenceIndexIndicator))[1]);

	return ArgumentManager.getArgumentFromIndex(number, argumentList).getSecondNumber(argumentList, loopCount);
    }

    public String getSecondNumber(ArrayList<Argument> argumentList) throws RecursiveLoopException {
	return getSecondNumber(argumentList, 1);
    }

    public String getSecondNumber(ArrayList<Argument> argumentList, int loopCount) throws RecursiveLoopException {
	if (!secondNumber.contains(String.valueOf(ArgumentManager.referenceIndicator)))
	    return secondNumber;
	if (loopCount == MAXLOOPS)
	    throw new RecursiveLoopException();
	String numberS = secondNumber.replaceAll("\\" + ArgumentManager.referenceIndicator, "");
	int number = Integer.valueOf(numberS.split(String.valueOf(ArgumentManager.referenceIndexIndicator))[1]);

	return ArgumentManager.getArgumentFromIndex(number, argumentList).getFirstNumber(argumentList, loopCount);
    }

    protected void updateNumbers(double value) {
	firstNumber = String.valueOf(value);
	secondNumber = String.valueOf(value);
    }

    @Override
    public int compareTo(Argument other) {
	int importanceOrder = new Integer(importance).compareTo(other.importance);
	if (importanceOrder != 0)
	    return -importanceOrder;
	return new Integer(index).compareTo(other.index);
    }

    public void updateEmptyReference(String newReference) {
	if (!sealed
		&& secondNumber.equals(new StringBuilder().append(ArgumentManager.referenceIndicator)
			.append(ArgumentManager.referenceIndicator).toString())
		&& newReference.contains(new StringBuilder().append(ArgumentManager.referenceIndicator)
			.append(ArgumentManager.referenceIndexIndicator))) {
	    secondNumber = newReference;
	    contents = contents.replace(new StringBuilder().append(ArgumentManager.referenceIndicator)
		    .append(ArgumentManager.referenceIndicator).toString(), newReference);
	}
    }

    public void seal() {
	sealed = true;
    }
}
