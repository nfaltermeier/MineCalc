package Blackop778.MineCalc.common;

import java.util.Comparator;

public class Argument implements Comparator<Argument> {
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

    public void getFirstNumber(Argument preArgument) {
	if (preArgument == null) {

	}
    }

    public String getOperator() {
	return operator;
    }

    public void getSecondNumber(Argument postArgument) {

    }

    @Override
    public int compare(Argument o1, Argument o2) {
	int importanceOrder = new Double(o1.importance).compareTo(o2.importance);
	if (importanceOrder != 0)
	    return importanceOrder;
	return -(new Integer(o1.index).compareTo(o2.index));

    }
}
