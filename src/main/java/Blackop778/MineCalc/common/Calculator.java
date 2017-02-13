package Blackop778.MineCalc.common;

import java.util.Stack;
import java.util.regex.Pattern;

import Blackop778.MineCalc.MineCalc;

public abstract class Calculator {
    public static void evaluate(String math, boolean useOOPS) {
	math = math.replaceAll("\\s", "");
	ArgumentManager arguments = new ArgumentManager();
	Stack<Integer> parenthesisStartIndex = new Stack<Integer>();
	parenthesisStartIndex.add(0);
	int parenthesisLevel = 0;

	for (int i = 0; i <= math.length(); i++) {
	    if (i == math.length()) {
		arguments.add(new Argument(arguments.size(), parenthesisLevel,
			math.substring(parenthesisStartIndex.pop(), i)));
	    } else {
		Character currentChar = math.charAt(i);
		if (currentChar.equals('(')) {
		    parenthesisStartIndex.add(i);
		    parenthesisLevel++;
		} else if (currentChar.equals(')')) {
		    arguments.add(new Argument(arguments.size(), parenthesisLevel,
			    math.substring(parenthesisStartIndex.pop(), i + 1)));
		    parenthesisLevel--;
		}
	    }
	}

	arguments.sort(new Argument(0, 0, "f"));

	while (true) {
	    IOperation op = null;
	    int index = 999;
	    String operator = "";
	    for (int i = 6; i > -1; i--) {
		IOperation[] level = MineCalc.operations.getLevel(i);
		for (int n = 0; n < level.length; n++) {
		    String[] current = level[n].getOperators();
		    for (int x = 0; x < current.length; x++) {
			int newIndex = arguments.get(0).contents.indexOf(current[x]);
			if (newIndex > -1 && newIndex < index) {
			    index = newIndex;
			    op = level[n];
			    operator = current[x];
			}
		    }
		}

		if (op != null)
		    break;
	    }

	    String contents = arguments.get(0).contents;
	    // Remove beginning and ending parenthesis first
	    findNeighboringNumbers(contents.substring(1, contents.length() - 1), operator, index - 1);
	}
    }

    public static void findNeighboringNumbers(String math, String symbol, int symbolStartIndex) {
	int index = 0;
	int lastIndex = 0;
	while (index != symbolStartIndex) {
	    lastIndex = index;
	    index = math.indexOf(symbol, lastIndex);
	}
	if (lastIndex != 0)
	    math = math.substring(lastIndex + symbol.length(), math.length());
	String[] maths = math.split(Pattern.quote(symbol));
	System.out.println(maths);
    }

    public static boolean isNumber(Character current, Character last, Character lastLast) {
	if (current.toString().matches("//d|//.|[lpiLPI]")) {
	    return true;
	}
    }
}
