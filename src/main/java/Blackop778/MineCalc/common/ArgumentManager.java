package Blackop778.MineCalc.common;

import java.util.ArrayList;

public class ArgumentManager
{
	private ArrayList<Argument> args;
	private boolean useOOPS;

	public ArgumentManager(boolean useOOPS)
	{
		args = new ArrayList<Argument>();
		this.useOOPS = useOOPS;
	}

	public void digest(String math, boolean useOOPs)
	{
		math = math.replaceAll("\\s", "");
		Type lastType = Type.NUMBER;
		int startIndex = 0;
		int parenthesisLevel = 0;
		String argumentPhrase = "";
		int phraseImportanceLevel = 0;
		int phraseCount = 0;
		boolean threeMode = true;
		int inIndex = 0;
		for(int i = 0; i <= math.length(); i++)
		{
			if(i == math.length())
			{
				argumentPhrase += math.substring(startIndex, i);
				args.add(new Argument(inIndex, phraseImportanceLevel + parenthesisLevel * 6, argumentPhrase));
			}
			else
			{
				Type type = getType(math.charAt(i), lastType);
				if(type.equals(Type.OPENPARENTHESIS))
				{
					parenthesisLevel++;
					threeMode = true;
				}
				if(!type.equals(lastType))
				{
					argumentPhrase += math.substring(startIndex, i);
					phraseCount++;
					if(phraseCount == 3 && threeMode)
					{
						args.add(new Argument(inIndex, phraseImportanceLevel + parenthesisLevel * 6, argumentPhrase));
						phraseCount = 0;
						argumentPhrase = "";
						threeMode = false;
					}
					else if(phraseCount == 2 && !threeMode)
					{
						args.add(new Argument(inIndex, phraseImportanceLevel + parenthesisLevel * 6, argumentPhrase));
						phraseCount = 0;
						argumentPhrase = "";
					}
				}
			}
		}
	}

	private String insertArrayReference(int i)
	{
		return "$#" + String.valueOf(i) + "#";
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

	public enum Type
	{
		NUMBER, OPENPARENTHESIS, CLOSEPARENTHESIS, DIVISION, MULTIPLICATION, ADDITION, SUBTRACTION, EXPONENTROOT, CUSTOMFUNCTION, JUNK;

	}
}
