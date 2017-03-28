package blackop778.mineCalc.core;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import blackop778.mineCalc.core.CalcExceptions.AllStandinsUsedException;
import blackop778.mineCalc.core.CalcExceptions.FancyRemainderException;
import blackop778.mineCalc.core.CalcExceptions.InvalidNumberException;
import blackop778.mineCalc.core.CalcExceptions.MultiplePointsException;
import blackop778.mineCalc.core.CalcExceptions.OperatorException;
import blackop778.mineCalc.core.CalcExceptions.ParenthesisException;
import blackop778.mineCalc.core.CalcExceptions.PreviousOutputException;
import blackop778.mineCalc.core.CalcExceptions.UnaryUsageException;
import blackop778.mineCalc.core.CalcExceptions.UsageException;

public abstract class Calculator {
    public static final Character[] STANDINCHARS = { '$', '#', '@', '"', ';', ':', '?', '&', '[', '{', ']', '}', '|',
	    '!', '=' };
    public static final Argument SORTING_HAT = new Argument(0, 0, "f");
    public static OperationHolder operations = new OperationHolder(true);
    public static Double consoleLastOutput = Double.NaN;

    public static double evaluate(String math, boolean useOOPS, Double last, boolean fancyRemainders)
	    throws CalcExceptions {
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
		    if (parenthesisLevel < 0)
			throw new ParenthesisException(false);
		}
	    }
	}
	if (parenthesisLevel > 0)
	    throw new ParenthesisException(true);

	arguments.sort(SORTING_HAT);

	// Solve everything
	while (true) {
	    String contents = arguments.get(0).contents;
	    if (contents.startsWith("(") && contents.endsWith(")")) {
		contents = contents.substring(1, contents.length() - 1);
	    }
	    if (onlyNumber(contents)) {
		if (arguments.updateMath(arguments.get(0).contents, contents)) {
		    break;
		}
	    } else {
		IOperation op = null;
		int index = 999;
		String operator = "";
		// Cycle through the OOPS levels to find the next operation we
		// should perform
		for (int i = operations.LEVELS; i > 0; i--) {
		    IOperation[] level = operations.getLevel(i);
		    // Cycle through the operations in the OOPS level
		    for (int n = 0; n < level.length; n++) {
			String[] current = level[n].getOperators();
			// Cycle through the operation strings for the given
			// operation
			for (int x = 0; x < current.length; x++) {
			    boolean run = true;
			    int startIndex = 0;
			    while (run) {
				run = false;
				int newIndex = contents.indexOf(current[x], startIndex);
				updateOP: if (newIndex > -1 && newIndex < index) {
				    if (current[x].equals("-")) {
					if (isNumber(contents.charAt(newIndex), tryCharAt(contents, newIndex - 1),
						tryCharAt(contents, newIndex - 2))) {
					    run = true;
					    startIndex = newIndex + 1;
					    break updateOP;
					}
				    }
				    index = newIndex;
				    op = level[n];
				    operator = current[x];
				}
			    }
			}
		    }

		    if (op != null && (useOOPS || i == 0)) {
			break;
		    }
		}
		if (index == 999)
		    throw new OperatorException();

		String trimmedContents = "";
		double answer = 0;
		if (op instanceof IBinaryOperation) {
		    // Solve based on the operation we found
		    Character replacer = findUnusedStandin(contents);
		    if (operator.equals("-")) {
			String[] temp = takeMinuses(contents, index);
			contents = temp[0];
			replacer = temp[1].charAt(0);
		    }
		    trimmedContents = binaryTrimToOperation(contents, operator, index, replacer);
		    String[] numbersS = trimmedContents.split(Pattern.quote(operator));
		    if (operator.equals("-")) {
			numbersS[0] = addMinus(numbersS[0], replacer);
			numbersS[1] = addMinus(numbersS[1], replacer);
			trimmedContents = addMinus(trimmedContents, replacer);
		    }
		    double[] numbers = { getDoubleValue(numbersS[0], last), getDoubleValue(numbersS[1], last) };
		    if (operator.equals("%") && arguments.size() == 1 && fancyRemainders
			    && (arguments.get(0).contents.equals("(" + trimmedContents + ")")
				    || arguments.get(0).contents.equals(trimmedContents)))
			throw new FancyRemainderException(numbers[0], numbers[1]);
		    answer = ((IBinaryOperation) op).evaluateFunction(numbers[0], numbers[1]);
		} else {
		    trimmedContents = unaryTrimToOperation(contents, operator, index);
		    double number = getDoubleValue(trimmedContents.replaceAll(Pattern.quote(operator), ""), last);
		    answer = ((IUnaryOperation) op).evaluateFunction(number);
		}
		if (arguments.get(0).contents.equals("(" + trimmedContents + ")")) {
		    trimmedContents = arguments.get(0).contents;
		}
		if (arguments.updateMath(trimmedContents, String.valueOf(answer))) {
		    break;
		}
	    }
	}

	return Double.valueOf(arguments.get(0).contents);
    }

    private static double getDoubleValue(String number, Double last)
	    throws PreviousOutputException, MultiplePointsException, InvalidNumberException, UsageException {
	boolean negative = false;
	double toReturn;
	if (number.startsWith("-")) {
	    negative = true;
	    number = number.substring(1);
	}

	if (number.equalsIgnoreCase("pi")) {
	    toReturn = Math.PI;
	} else if (number.equalsIgnoreCase("e")) {
	    toReturn = Math.E;
	} else if (number.equalsIgnoreCase("l")) {
	    if (last == null || last.equals(Double.NaN))
		throw new PreviousOutputException();
	    else {
		toReturn = last;
	    }
	} else if (number.equals(""))
	    throw new UsageException();
	else {
	    try {
		toReturn = Double.valueOf(number);
	    } catch (NumberFormatException e) {
		if (e.getMessage().equals("multiple points"))
		    throw new MultiplePointsException();
		else
		    throw new InvalidNumberException(" " + number);
	    }
	}

	return negative ? -toReturn : toReturn;
    }

    private static String binaryTrimToOperation(String math, String operationSymbol, int symbolStartIndex,
	    Character numberStandin) throws AllStandinsUsedException, UsageException, InvalidNumberException {
	int index = 0;
	int lastIndex = 0;
	String[] maths = math.split(Pattern.quote(operationSymbol));

	try {
	    // Isolate the first number
	    String math1 = maths[0];
	    lastIndex = math1.length();
	    for (index = lastIndex - 1; index > -1; index--) {
		if (!isNumber(math1.charAt(index), tryCharAt(math1, index - 1), tryCharAt(math1, index - 2),
			numberStandin)) {
		    index++;
		    break;
		}

	    }
	    if (index == -1) {
		index = 0;
	    }
	    if (index != lastIndex) {
		math1 = math1.substring(index, lastIndex);
	    }

	    // Isolate the second number
	    String math2 = maths[1];

	    lastIndex = 0;
	    for (index = lastIndex; index < math2.length(); index++) {
		if (!isNumber(math2.charAt(index), tryCharAt(math2, index - 1), tryCharAt(math2, index - 2),
			numberStandin)) {
		    break;
		}
	    }
	    if (index != math2.length() - 1) {
		math2 = math2.substring(lastIndex, index);
	    }

	    return math1 + operationSymbol + math2;
	} catch (ArrayIndexOutOfBoundsException e) {
	    throw new UsageException();
	}
    }

    private static String unaryTrimToOperation(String math, String operationSymbol, int symbolStartIndex)
	    throws AllStandinsUsedException, UnaryUsageException {
	// Remove leading parenthesis
	if (new Character('(').equals(tryCharAt(math, 0))
		&& new Character(')').equals(tryCharAt(math, symbolStartIndex - 1))) {
	    math = math.substring(1, symbolStartIndex - 1) + math.substring(symbolStartIndex, math.length());
	    symbolStartIndex -= 2;
	}
	// Remove following parenthesis
	if (new Character('(').equals(tryCharAt(math, symbolStartIndex + operationSymbol.length() + 1))
		&& new Character(')').equals(tryCharAt(math, math.length() - 1))) {
	    math = math.substring(0, symbolStartIndex + operationSymbol.length() + 1)
		    + math.substring(symbolStartIndex + operationSymbol.length() + 2, math.length() - 1);
	}
	int numLocation = 0;
	Character currentChar = tryCharAt(math, symbolStartIndex - 1);
	Character lastChar = tryCharAt(math, symbolStartIndex - 2);
	Character lastLastChar = tryCharAt(math, symbolStartIndex - 3);
	int i = 0;
	// Is the number before the operation
	if (currentChar != null && isNumber(currentChar, lastChar, lastLastChar)) {
	    numLocation = -1;
	    for (i = 1; symbolStartIndex - i > -1 && isNumber(currentChar, lastChar, lastLastChar); i++) {
		lastLastChar = lastChar;
		lastChar = currentChar;
		currentChar = math.charAt(symbolStartIndex - i);
	    }
	    if (symbolStartIndex - i == -1)
		i--;
	    math = math.substring(symbolStartIndex - i, symbolStartIndex + operationSymbol.length());
	}
	currentChar = tryCharAt(math, symbolStartIndex + operationSymbol.length());
	lastChar = tryCharAt(math, symbolStartIndex + operationSymbol.length() - 1);
	lastLastChar = tryCharAt(math, symbolStartIndex + operationSymbol.length() - 2);
	// Is the number after the operation
	if (currentChar != null && isNumber(currentChar, lastChar, lastLastChar)) {
	    if (numLocation == -1)
		throw new UnaryUsageException();
	    numLocation = 1;
	    for (i = 1; symbolStartIndex + operationSymbol.length() + i < math.length()
		    && isNumber(currentChar, lastChar, lastLastChar); i++) {
		lastLastChar = lastChar;
		lastChar = currentChar;
		currentChar = math.charAt(symbolStartIndex + operationSymbol.length() + i);
	    }
	    math = math.substring(symbolStartIndex, symbolStartIndex + operationSymbol.length() + i);
	}
	if (numLocation == 0)
	    throw new UnaryUsageException();
	return math;
    }

    /**
     * Gets the character at index in string, and returns null instead of
     * StringIndexOutOfBoundsException
     */
    private static Character tryCharAt(String string, int index) {
	try {
	    return string.charAt(index);
	} catch (StringIndexOutOfBoundsException e) {
	    return null;
	}
    }

    private static String concatNullableCharacters(Character first, Character... others) {
	StringBuilder toReturn = new StringBuilder();
	if (first != null) {
	    toReturn.append(first);
	}
	for (Character c : others) {
	    if (c != null) {
		toReturn.append(c);
	    }
	}

	return toReturn.toString();
    }

    private static boolean isNumber(Character current, Character last, Character lastLast)
	    throws AllStandinsUsedException {
	return isNumber(current, last, lastLast, findUnusedStandin(concatNullableCharacters(current, last, lastLast)));
    }

    private static boolean isNumber(Character current, Character last, Character lastLast, Character numberStandIn) {
	if (current.toString().matches("\\d|\\.|[lpieLPIE]|" + Pattern.quote(numberStandIn.toString())))
	    return true;
	else if (current.equals('-') && !(new Character('/').equals(lastLast))) {
	    if (last == null)
		return true;
	    else if (!last.toString().matches("\\d|\\.|[lpieLPIE]|" + Pattern.quote(numberStandIn.toString())))
		return true;
	}

	return false;
    }

    private static Character findUnusedStandin(String text) throws AllStandinsUsedException {
	for (Character c : STANDINCHARS) {
	    if (!text.contains(new StringBuilder().append(c).toString()))
		return c;
	}

	StringBuilder chars = new StringBuilder();
	for (Character c : STANDINCHARS) {
	    chars.append("'").append(c).append("', ");
	}
	throw new AllStandinsUsedException(chars.toString().substring(0, chars.toString().length() - 2));
    }

    /**
     * 
     * @param toChange
     * @param unchangedIndex
     * @return the changed string followed by the char the extra minus signs
     *         were replaced by
     * @throws AllStandinsUsedException
     */
    private static String[] takeMinuses(String toChange, int unchangedIndex) throws AllStandinsUsedException {
	Character replacement = findUnusedStandin(toChange);

	toChange = toChange.replaceAll(Pattern.quote("-"), Matcher.quoteReplacement(replacement.toString()));
	toChange = toChange.substring(0, unchangedIndex) + "-"
		+ toChange.substring(unchangedIndex + 1, toChange.length());

	return new String[] { toChange, replacement.toString() };
    }

    private static String addMinus(String math, Character minusReplacer) {
	return math.replaceAll(Pattern.quote(minusReplacer.toString()), "-");
    }

    private static boolean onlyNumber(String toCheck) throws AllStandinsUsedException {
	Character lastLast;
	Character last = null;
	Character current = null;
	for (int i = 0; i < toCheck.length(); i++) {
	    lastLast = last;
	    last = current;
	    current = toCheck.charAt(i);
	    if (!isNumber(current, last, lastLast))
		return false;
	}

	return true;
    }
}
