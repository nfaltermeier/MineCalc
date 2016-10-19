package Blackop778.MineCalc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import Blackop778.MineCalc.MineCalc;

public class ArgumentManager {
    private ArrayList<Argument> arguments;
    private boolean useOOPS;

    public ArgumentManager(boolean useOOPS) {
	arguments = new ArrayList<Argument>();
	this.useOOPS = useOOPS;
    }

    public void digest(String math) {
	math = math.replaceAll("\\s", "");
	Type lastType = Type.NUMBER;
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
	int typesUntilParen = getTypesUntilTarget(math, 0, Type.CLOSEPARENTHESIS, lastType);
	for (int i = 0; i <= math.length(); i++) {
	    // We're out of input so we have to take what we've got
	    if (i == math.length()) {
		if (!lastType.equals(Type.CLOSEPARENTHESIS)) {
		    argumentPhrase += math.substring(startIndex, i);
		    arguments.add(new Argument(arguments.size(), phraseImportanceLevel + parenthesisLevel * 6,
			    argumentPhrase));
		}
	    } else {
		char currentChar = math.charAt(i);
		Type type = getType(currentChar, lastType);
		if (type.equals(Type.ROOT))
		    lastType = type;
		if (!type.equals(lastType)) {
		    if (lastType.equals(Type.CLOSEPARENTHESIS)) {
			startIndex = i;
		    } else {
			typesUntilParen--;
			argumentPhrase = argumentPhrase + math.substring(startIndex, i);
			startIndex = i;
			phraseCount++;
		    }
		    if (type.equals(Type.OPENPARENTHESIS)) {
			threeMode = true;
			argumentPhrase = argumentPhrase + insertArrayReference(arguments.size() + 1);
			arguments.add(new Argument(arguments.size(), phraseImportanceLevel + parenthesisLevel * 6,
				argumentPhrase));
			parenthesisStartIndex.add(arguments.size() - 1);
			parenthesisLevel++;
			phraseCount = -1;
			argumentPhrase = "";
			startIndex++;
		    } else if (phraseCount > 2) {
			arguments.add(new Argument(arguments.size(), phraseImportanceLevel + parenthesisLevel * 6,
				argumentPhrase));
			phraseCount = 0;
			if (typesUntilParen == 0) {
			    argumentPhrase = insertArrayReference(parenthesisStartIndex.pop());
			    parenthesisLevel--;
			    typesUntilParen = getTypesUntilTarget(math, i, Type.CLOSEPARENTHESIS, lastType);
			} else {
			    argumentPhrase = insertArrayReference(arguments.size() - 1);
			}

			threeMode = false;
		    } else if (phraseCount == 2 && !threeMode) {
			arguments.add(new Argument(arguments.size(), phraseImportanceLevel + parenthesisLevel * 6,
				argumentPhrase));
			phraseCount = 0;
			if (typesUntilParen == 0) {
			    argumentPhrase = insertArrayReference(parenthesisStartIndex.pop());
			    parenthesisLevel--;
			    typesUntilParen = getTypesUntilTarget(math, i, Type.CLOSEPARENTHESIS, lastType);
			} else {
			    argumentPhrase = insertArrayReference(arguments.size() - 1);
			}
		    }
		} else if (type.equals(Type.OPENPARENTHESIS)) {
		    argumentPhrase = "0+" + insertArrayReference(arguments.size() + 1);
		    arguments.add(new Argument(arguments.size(), phraseImportanceLevel + parenthesisLevel * 6,
			    argumentPhrase));
		    parenthesisStartIndex.add(arguments.size() - 1);
		    parenthesisLevel++;
		    phraseCount = -1;
		    argumentPhrase = "";
		    startIndex++;
		} else if (type.equals(Type.CLOSEPARENTHESIS)) {
		    argumentPhrase = insertArrayReference(parenthesisStartIndex.pop());
		    parenthesisLevel--;
		}
		lastType = type;
	    }
	}

	arguments.trimToSize();
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
	    Type operatorType = Type.JUNK;
	    for (int n = 0; n < MineCalc.functions.size(); n++) {
		Type type = Type.JUNK;
		for (int x = 0; x < args[i].getOperator().length(); x++) {
		    type = MineCalc.functions.get(n).getType(args[i].getOperator().charAt(x), type);
		}
		if (!type.equals(Type.JUNK))
		    operatorType = type;
	    }
	    if (!operatorType.equals(Type.CUSTOMFUNCTION)) {
		IFunction handler = null;
		handlerSearch: for (int n = 0; n < MineCalc.functions.size(); n++) {
		    if (operatorType.equals(MineCalc.functions.get(n).getHandledType())) {
			handler = MineCalc.functions.get(n);
			break handlerSearch;
		    }
		}
		double answer = handler.evaluateFunction(Double.valueOf(args[i].getFirstNumber(arguments)),
			Double.valueOf(args[i].getSecondNumber(arguments)));
		args[i].updateNumbers(answer);
	    } else {

	    }
	}
	return Double.valueOf(args[args.length - 1].getSecondNumber(arguments));
    }

    private String insertArrayReference(int i) {
	return "$#" + String.valueOf(i) + "$";
    }

    // TODO: Use IFunction list to get types instead
    public static Type getType(Character character, Type lastType) {
	if (character.equals('('))
	    return Type.OPENPARENTHESIS;
	if (character.equals(')'))
	    return Type.CLOSEPARENTHESIS;
	if (!character.equals('.')) {
	    if (!character.toString().equalsIgnoreCase("l")) {
		if (!character.toString().equalsIgnoreCase("p")) {
		    if (!character.toString().equalsIgnoreCase("i")) {
			if (!(character.equals('-')
				&& (!lastType.equals(Type.NUMBER) && !lastType.equals(Type.DIVISION)))) {
			    try {
				Double.valueOf(String.valueOf(character));
			    } catch (NumberFormatException e) {
				if (character.equals('/'))
				    return Type.DIVISION;
				if (character.equals('*') || character.toString().equalsIgnoreCase("X"))
				    return Type.MULTIPLICATION;
				if (character.equals('-')) {
				    if (lastType.equals(Type.DIVISION))
					return Type.ROOT;
				    return Type.SUBTRACTION;
				}
				if (character.equals('+'))
				    return Type.ADDITION;
				if (character.equals('^'))
				    return Type.EXPONENT;
				return Type.JUNK;
			    }
			}
		    }
		}
	    }
	}

	return Type.NUMBER;
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
	    Type type = getType(string.charAt(i), lastType);
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

    public enum Type {
	NUMBER, OPENPARENTHESIS, CLOSEPARENTHESIS, DIVISION, MULTIPLICATION, ADDITION, SUBTRACTION, EXPONENT, ROOT, CUSTOMFUNCTION, JUNK, MODULO;

    }
}
