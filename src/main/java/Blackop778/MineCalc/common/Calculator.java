package Blackop778.MineCalc.common;

import java.util.Stack;
import java.util.regex.Pattern;

import Blackop778.MineCalc.MineCalc;

public abstract class Calculator {
    public static final Argument SORTING_HAT = new Argument(0, 0, "f");

    public static double evaluate(String math, boolean useOOPS) throws CalcExceptions {
	math = math.replaceAll("\\s", "");
	ArgumentManager arguments = new ArgumentManager();
	Stack<Integer> parenthesisStartIndex = new Stack<Integer>();
	parenthesisStartIndex.add(0);
	int parenthesisLevel = 0;

	// Divide into arguments by parenthesis
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

	arguments.sort(SORTING_HAT);

	// Solve everything
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
	    if (contents.startsWith("(") && contents.endsWith(")")) {
		contents = contents.substring(1, contents.length() - 1);
	    }
	    String trimmedContents = trimToOperation(contents, operator, index - 1);
	    String[] numbersS = trimmedContents.split(Pattern.quote(operator));
	    double[] numbers = { Double.valueOf(numbersS[0]), Double.valueOf(numbersS[1]) };
	    double answer = op.evaluateFunction(numbers[0], numbers[1]);
	    if (arguments.get(0).contents.equals("(" + trimmedContents + ")"))
		trimmedContents = arguments.get(0).contents;
	    if (arguments.updateMath(trimmedContents, String.valueOf(answer)))
		break;
	}

	return Double.valueOf(arguments.get(0).contents);
    }

    public static String trimToOperation(String math, String operationSymbol, int symbolStartIndex) {
	int index = 0;
	int lastIndex = 0;
	// Eliminate other instances of operationSymbol
	/**
	 * while (index != symbolStartIndex) { lastIndex = index; index =
	 * math.indexOf(operationSymbol, lastIndex); } if (lastIndex != 0) math
	 * = math.substring(lastIndex + operationSymbol.length(),
	 * math.length());
	 */
	String[] maths = math.split(Pattern.quote(operationSymbol));

	// Isolate the first number
	String math1 = maths[0];
	lastIndex = math1.length();
	for (index = lastIndex - 1; index > -1; index--) {
	    if (!isNumber(math1.charAt(index), tryCharAt(math1, index - 1), tryCharAt(math1, index - 2))) {
		index++;
		break;
	    }

	}
	if (index == -1)
	    index++;
	if (index != math1.length() - 1) {
	    math1 = math1.substring(index, lastIndex);
	}

	// Isolate the second number
	String math2 = maths[1];
	lastIndex = 0;
	for (index = lastIndex; index < math2.length(); index++) {
	    if (!isNumber(math2.charAt(index), tryCharAt(math2, index - 1), tryCharAt(math2, index - 2)))
		break;
	}
	if (index != math2.length() - 1) {
	    math2 = math2.substring(lastIndex, index);
	}
	return math1 + operationSymbol + math2;
    }

    /**
     * Gets the character at index in string, and returns null instead of
     * StringIndexOutOfBoundsException
     */
    public static Character tryCharAt(String string, int index) {
	try {
	    return string.charAt(index);
	} catch (StringIndexOutOfBoundsException e) {
	    return null;
	}
    }

    public static boolean isNumber(Character current, Character last, Character lastLast) {
	if (current.toString().matches("\\d|\\.|[lpiLPI]")) {
	    return true;
	} else if (current.equals('-')
		&& ((new Character('-').equals(last) || last == null) && !(new Character('/').equals(lastLast)))) {
	    return true;
	}

	return false;
    }
}
