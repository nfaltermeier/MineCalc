package Blackop778.MineCalc.common;

import java.util.ArrayList;
import java.util.Stack;

public class ArgumentManager
{
	private ArrayList<Argument> args;
	private boolean useOOPS;

	public ArgumentManager(boolean useOOPS)
	{
		args = new ArrayList<Argument>();
		this.useOOPS = useOOPS;
	}

	public void digest(String math)
	{
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
		for(int i = 0; i <= math.length(); i++)
		{
			// We're out of input so we have to take what we've got
			if(i == math.length())
			{
				argumentPhrase += math.substring(startIndex, i);
				args.add(new Argument(args.size() - 1, phraseImportanceLevel + parenthesisLevel * 6, argumentPhrase));
			}
			else
			{
				Type type = getType(math.charAt(i), lastType);
				if(!type.equals(lastType))
				{
					typesUntilParen--;
					argumentPhrase = argumentPhrase + math.substring(startIndex, i);
					startIndex = i;
					phraseCount++;
					if(type.equals(Type.OPENPARENTHESIS))
					{
						threeMode = true;
						argumentPhrase = argumentPhrase + insertArrayReference(args.size() - 1 + 1);
						args.add(new Argument(args.size() - 1, phraseImportanceLevel + parenthesisLevel * 6,
								argumentPhrase));
						parenthesisStartIndex.add(args.size() - 1);
						parenthesisLevel++;
						phraseCount = 0;
						argumentPhrase = "";
						i++;
						startIndex++;
						typesUntilParen--;
					}
					else if(phraseCount > 2)
					{
						args.add(new Argument(args.size() - 1, phraseImportanceLevel + parenthesisLevel * 6,
								argumentPhrase));
						phraseCount = 0;
						if(typesUntilParen == 0)
						{
							argumentPhrase = insertArrayReference(parenthesisStartIndex.pop());
							parenthesisLevel--;
							i++;
							startIndex++;
							typesUntilParen = getTypesUntilTarget(math, i, Type.CLOSEPARENTHESIS, lastType);
						}
						else
						{
							argumentPhrase = insertArrayReference(args.size() - 1);
						}

						threeMode = false;
					}
					else if(phraseCount == 2 && !threeMode)
					{
						args.add(new Argument(args.size() - 1, phraseImportanceLevel + parenthesisLevel * 6,
								argumentPhrase));
						phraseCount = 0;
						if(typesUntilParen == 0)
						{
							argumentPhrase = insertArrayReference(parenthesisStartIndex.pop());
							parenthesisLevel--;
							i++;
							startIndex++;
							typesUntilParen = getTypesUntilTarget(math, i, Type.CLOSEPARENTHESIS, lastType);
						}
						else
						{
							argumentPhrase = insertArrayReference(args.size() - 1);
						}
					}
				}
				lastType = type;
			}
		}

		args.trimToSize();
	}

	private String insertArrayReference(int i)
	{
		return "$#" + String.valueOf(i) + "$";
	}

	public static Type getType(Character character, Type lastType)
	{
		if(character.equals('('))
			return Type.OPENPARENTHESIS;
		if(character.equals(')'))
			return Type.CLOSEPARENTHESIS;
		if(!character.equals('.'))
		{
			if(!character.toString().equalsIgnoreCase("l"))
			{
				if(!character.toString().equalsIgnoreCase("p"))
				{
					if(!character.toString().equalsIgnoreCase("i"))
					{
						if(!(character.equals('-')
								&& (!lastType.equals(Type.NUMBER) && !lastType.equals(Type.DIVISION))))
						{
							try
							{
								Double.valueOf(String.valueOf(character));
							}
							catch(NumberFormatException e)
							{
								if(character.equals('/'))
									return Type.DIVISION;
								if(character.equals('*') || character.toString().equalsIgnoreCase("X"))
									return Type.MULTIPLICATION;
								if(character.equals('-'))
								{
									if(lastType.equals(Type.DIVISION))
										return Type.EXPONENTROOT;
									return Type.SUBTRACTION;
								}
								if(character.equals('+'))
									return Type.ADDITION;
								if(character.equals('^'))
									return Type.EXPONENTROOT;
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
	public static int getTypesUntilTarget(String string, int index, Type typeToFind, Type lastType)
	{
		ArrayList<Type> differingTypes = new ArrayList<Type>();
		for(int i = index; i < string.length(); i++)
		{
			Type type = getType(string.charAt(i), lastType);
			if(type.equals(typeToFind))
				return differingTypes.size() - 1;
			if(differingTypes.size() == 0)
				differingTypes.add(type);
			else if(!differingTypes.get(differingTypes.size() - 1).equals(type))
				differingTypes.add(type);
		}

		return -1;
	}

	public enum Type
	{
		NUMBER, OPENPARENTHESIS, CLOSEPARENTHESIS, DIVISION, MULTIPLICATION, ADDITION, SUBTRACTION, EXPONENTROOT, CUSTOMFUNCTION, JUNK;

	}
}
