package Blackop778.MineCalc.common;

public class ArgumentManager
{
	private Argument[] args;
	private int inIndex;

	public ArgumentManager()
	{
		args = new Argument[0];
		inIndex = 0;
	}

	public void digest(String math)
	{
		math = math.replaceAll("\\s", "");
		Type lastType = Type.CLOSEPARENTHESIS;
		int lastIndex = -1;
		int parenthesisLevel = 0;
		String argumentPhrase;
		for(int i = 0; i < math.length(); i++)
		{
			Type type = getType(math.charAt(i), lastType);
		}
	}

	public void modifyOthersImportance(Argument notChanged, double amountChanged)
	{

	}

	public static String insertArrayReference(int i)
	{
		return "$#" + String.valueOf(i) + "#";
	}

	private Argument[] extendArray(Argument[] array)
	{
		return extendArray(array, 1);
	}

	private Argument[] extendArray(Argument[] array, int lengthToExtend)
	{
		Argument[] newArray = new Argument[array.length + lengthToExtend];
		for(int i = 0; i < array.length; i++)
		{
			newArray[i] = array[i];
		}

		return newArray;
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
								&& (!lastType.equals(Type.NUMBER) && !lastType.equals(Type.DIVISION)))
						{
							try
							{
								Double.valueOf(String.valueOf(character));
							}
							catch(NumberFormatException e)
							{
								return Type.FUNCTION;
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
		NUMBER, FUNCTION, OPENPARENTHESIS, CLOSEPARENTHESIS, DIVISION;

	}
}
