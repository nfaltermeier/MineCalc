package Blackop778.MineCalc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import Blackop778.MineCalc.MineCalc;

public class ArgumentManager {
    private ArrayList<Argument> arguments;
    private boolean useOOPS;
    public static final char referenceIndicator = '$';
    public static final char referenceIndexIndicator = '#';

    public ArgumentManager(boolean useOOPS) {
	arguments = new ArrayList<Argument>();
	this.useOOPS = useOOPS;
    }

    /**
     * Ensure that all the IFunctions have been added to the ArrayList before
     * executing
     * 
     * @param math
     */
    public void digest(String math) {
	// Clean the input
	math = math.replaceAll("\\s", "");
	math = math.replaceAll("\\" + referenceIndicator, "");
	math = math.replaceAll("\\" + referenceIndexIndicator, "");
	FunctionType lastType = new FunctionType(null, Type.NUMBER);
	// The index of the start of the current Type
	int startIndex = 0;
	// How many layers of parenthesis we're in
	int parenthesisLevel = 0;
	// The index at which we increased parenthesis level
	Stack<Integer> parenthesisStartIndex = new Stack<Integer>();
	// The current argument we've parsed so far
	String argumentPhrase = "";
	// To be implemented
	int phraseImportanceLevel = 0;
	// How many phrases are in the argumentPhrase
	int phraseCount = 0;
	// Whether or not we should get 3 explicit arguments
	boolean threeMode = true;
	// How many Types until a closed parenthesis
	int typesUntilParen = getTypesUntilTarget(math, 0, Type.CLOSEPARENTHESIS, lastType.type);
	// The FunctionType for this argument
	IFunction argumentFunction = null;
	char lastChar = referenceIndicator;
	for (int i = 0; i <= math.length(); i++) {
	    // We're out of input so we have to take what we've got
	    if (i == math.length()) {
		if (!lastType.type.equals(Type.CLOSEPARENTHESIS)) {
		    argumentPhrase += math.substring(startIndex, i);
		    arguments.add(new Argument(arguments.size(), phraseImportanceLevel + parenthesisLevel * 6,
			    argumentPhrase, argumentFunction));
		} else {
		    getArgumentFromIndex(parenthesisStartIndex.pop())
			    .updateEmptyReference(insertArgumentReference(arguments.size() - 1));
		}
	    } else {
		// TODO: Remove for release
		char currentChar = math.charAt(i);
		FunctionType functionType = getType(currentChar, lastChar, lastType.type);
		if (!functionType.type.equals(lastType.type)) {
		    typesUntilParen--;
		    argumentPhrase = argumentPhrase + math.substring(startIndex, i);
		    startIndex = i;
		    phraseCount++;
		    if (lastType.equals(Type.CLOSEPARENTHESIS)) {
			getArgumentFromIndex(parenthesisStartIndex.pop())
				.updateEmptyReference(insertArgumentReference(arguments.size() - 1));
		    }
		    if (!functionType.type.equals(Type.NUMBER) && !lastType.type.equals(Type.NUMBER)
			    && !functionType.type.equals(Type.OPENPARENTHESIS)) {
			phraseCount--;
		    }
		    if ((phraseCount == 2 && threeMode) || (phraseCount == 1 && !threeMode)) {
			argumentFunction = lastType.function;
		    }
		    if (functionType.type.equals(Type.OPENPARENTHESIS)) {
			threeMode = true;
			argumentPhrase = argumentPhrase + insertEmptyArgumentReference();
			arguments.add(new Argument(arguments.size(), phraseImportanceLevel + parenthesisLevel * 6,
				argumentPhrase, argumentFunction));
			parenthesisStartIndex.add(arguments.size() - 1);
			parenthesisLevel++;
			phraseCount = -1;
			argumentPhrase = "";
			startIndex++;
		    } else if (phraseCount > 2) {
			arguments.add(new Argument(arguments.size(), phraseImportanceLevel + parenthesisLevel * 6,
				argumentPhrase, argumentFunction));
			phraseCount = 0;
			if (typesUntilParen == 0) {
			    parenthesisLevel--;
			    typesUntilParen = getTypesUntilTarget(math, i + 1, Type.CLOSEPARENTHESIS, lastType.type)
				    + 1;
			    startIndex = i + 1;
			}
			argumentPhrase = insertArgumentReference(arguments.size() - 1);
			threeMode = false;
		    } else if (phraseCount == 2 && !threeMode) {
			arguments.add(new Argument(arguments.size(), phraseImportanceLevel + parenthesisLevel * 6,
				argumentPhrase, argumentFunction));
			phraseCount = 0;
			if (typesUntilParen == 0) {
			    parenthesisLevel--;
			    typesUntilParen = getTypesUntilTarget(math, i + 1, Type.CLOSEPARENTHESIS, lastType.type)
				    + 1;
			    startIndex = i + 1;
			}
			argumentPhrase = insertArgumentReference(arguments.size() - 1);
		    }
		} else if (functionType.type.equals(Type.OPENPARENTHESIS)) {
		    for (int n = 0; n < MineCalc.functions.size(); n++) {
			FunctionType typ = MineCalc.functions.get(n).getType('+', referenceIndicator, Type.NUMBER);
			if (typ.type.equals(Type.ADDITION)) {
			    argumentFunction = typ.function;
			    break;
			}
		    }
		    arguments.add(new Argument(arguments.size(), phraseImportanceLevel + parenthesisLevel * 6,
			    "0+" + insertEmptyArgumentReference(), argumentFunction));
		    parenthesisStartIndex.add(arguments.size() - 1);
		    parenthesisLevel++;
		    phraseCount = -1;
		    argumentPhrase = "";
		    startIndex++;
		} else if (functionType.type.equals(Type.CLOSEPARENTHESIS)) {
		    // TODO: Add '0+$#x$ to nested closing parenthesis
		    /**
		     * for (int n = 0; n < MineCalc.functions.size(); n++) {
		     * FunctionType typ = MineCalc.functions.get(n).getType('+',
		     * referenceIndicator, Type.NUMBER); if
		     * (typ.type.equals(Type.ADDITION)) { argumentFunction =
		     * typ.function; break; } } arguments.add(new
		     * Argument(arguments.size(), phraseImportanceLevel +
		     * parenthesisLevel * 6,
		     * insertArgumentReference(parenthesisStartIndex.pop()) +
		     * "+" + insertArgumentReference(arguments.size() - 1),
		     * argumentFunction));
		     */
		    getArgumentFromIndex(parenthesisStartIndex.pop())
			    .updateEmptyReference(insertArgumentReference(arguments.size() - 1));
		    parenthesisLevel--;
		    startIndex = i;
		}
		lastType = functionType;
		lastChar = currentChar;
	    }
	}

	arguments.trimToSize();
	for (int i = 0; i < arguments.size(); i++) {
	    arguments.get(i).seal();
	}
    }

    /**
     * Make sure digest(String) has been called first. Functions.addFunctions()
     * needs to be called as well.
     * 
     * @return the answer
     */
    public double evaluate() throws CalcExceptions {
	Argument[] args = arguments.toArray(new Argument[0]);
	Arrays.sort(args);
	for (int i = 0; i < args.length; i++) {
	    double answer = args[i].function.evaluateFunction(Double.valueOf(args[i].getFirstNumber(arguments)),
		    Double.valueOf(args[i].getSecondNumber(arguments)));
	    args[i].updateNumbers(answer);
	}
	return Double.valueOf(args[args.length - 1].getSecondNumber(arguments));
    }

    public Argument getArgumentFromIndex(int index) {
	return getArgumentFromIndex(index, arguments);
    }

    public static Argument getArgumentFromIndex(int index, ArrayList<Argument> args) {
	for (int i = 0; i < args.size(); i++) {
	    if (args.get(i).index == index)
		return args.get(i);
	}

	throw new IndexOutOfBoundsException();
    }

    private final String insertArgumentReference(int i) {
	return new StringBuilder().append(referenceIndicator).append(referenceIndexIndicator).append(i)
		.append(referenceIndicator).toString();
    }

    private final String insertEmptyArgumentReference() {
	return new StringBuilder().append(referenceIndicator).append(referenceIndicator).toString();
    }

    public static FunctionType getType(Character character, Character lastCharacter, Type lastType) {
	if (character.equals('('))
	    return new FunctionType(null, Type.OPENPARENTHESIS);
	if (character.equals(')'))
	    return new FunctionType(null, Type.CLOSEPARENTHESIS);
	try {
	    Double.valueOf(String.valueOf(character));
	    return new FunctionType(null, Type.NUMBER);
	} catch (NumberFormatException e) {
	    if (character.equals('.') || character.toString().equalsIgnoreCase("l")
		    || character.toString().equalsIgnoreCase("p") || character.toString().equalsIgnoreCase("i")
		    || (character.equals('-') && (!lastType.equals(Type.NUMBER) && !lastType.equals(Type.DIVISION))))
		return new FunctionType(null, Type.NUMBER);
	    FunctionType toReturn = new FunctionType(null, Type.JUNK);
	    for (int i = 0; i < MineCalc.functions.size(); i++) {
		FunctionType possible = MineCalc.functions.get(i).getType(character, lastCharacter, lastType);
		if (toReturn.type.compareTo(possible.type) < 0) {
		    toReturn = possible;
		}
	    }
	    return toReturn;
	}
    }

    /**
     * @param string
     *            The string to search
     * @param index
     *            Index of the string to start at
     * @param typeToFind
     *            the Type to find in string
     * @param lastType
     *            the last type you got
     * @return The number of Types until the target Type, or -1 if none
     */
    public static int getTypesUntilTarget(String string, int index, Type typeToFind, Type lastType) {
	ArrayList<Type> differingTypes = new ArrayList<Type>();
	for (int i = index; i < string.length(); i++) {
	    Type type;
	    if (i < 0) {
		type = getType(string.charAt(i), string.charAt(i - 1), lastType).type;
	    } else {
		type = getType(string.charAt(i), referenceIndicator, lastType).type;
	    }
	    if (type.equals(typeToFind))
		return differingTypes.size();
	    if (differingTypes.size() == 0) {
		differingTypes.add(type);
	    } else if (!differingTypes.get(differingTypes.size() - 1).equals(type)) {
		differingTypes.add(type);
	    }
	}

	return -1;
    }

    public void clear() {
	arguments = new ArrayList<Argument>();
    }

    public enum Type {
	JUNK, CUSTOMFUNCTION, ADDITION, SUBTRACTION, DIVISION, MODULO, MULTIPLICATION, EXPONENT, ROOT, NUMBER, OPENPARENTHESIS, CLOSEPARENTHESIS;
    }

    public static class FunctionType {
	public Type type;
	public IFunction function;

	public FunctionType(IFunction function, Type type) {
	    this.type = type;
	    this.function = function;
	}
    }
}
