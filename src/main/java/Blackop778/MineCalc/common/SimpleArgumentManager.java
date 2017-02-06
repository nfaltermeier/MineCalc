package Blackop778.MineCalc.common;

import java.util.ArrayList;
import java.util.Stack;

public abstract class SimpleArgumentManager {
    public static void evaluate(String math, boolean useOOPS) {
	math = math.replaceAll("\\s", "");
	ArrayList<Argument> arguments = new ArrayList<Argument>();
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
    }
}
