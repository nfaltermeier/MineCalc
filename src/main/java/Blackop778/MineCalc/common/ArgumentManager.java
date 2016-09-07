package Blackop778.MineCalc.common;

import javax.annotation.Nullable;

public class ArgumentManager
{
	private String[] args;
	private int inIndex;

	public ArgumentManager()
	{
		args = new String[0];
		inIndex = 0;
	}

	public @Nullable String getAnswer()
	{
		for(int i = 0; i < args.length; i++)
		{
			if(!args[i].contains("$#"))
			{

			}
		}

		return null;
	}

	public void addArg(String toAdd)
	{
		args = extendArray(args);
		args[inIndex] = toAdd;
		inIndex++;
	}

	public static String insertArrayReference(int i)
	{
		return "$#" + String.valueOf(i) + "#";
	}

	public static String[] extendArray(String[] array)
	{
		return extendArray(array, 1);
	}

	public static String[] extendArray(String[] array, int lengthToExtend)
	{
		String[] newArray = new String[array.length + lengthToExtend];
		for(int i = 0; i < array.length; i++)
		{
			newArray[i] = array[i];
		}

		return newArray;
	}
}
