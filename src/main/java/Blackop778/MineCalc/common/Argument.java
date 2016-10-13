package Blackop778.MineCalc.common;

import java.util.Comparator;

import Blackop778.MineCalc.common.ArgumentManager.Type;

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
	Type lastType = Type.NUMBER;
	for (int i = 0; i < contents.length(); i++) {

	}
    }

    public void getFirstNumber(Argument preArgument) {

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
