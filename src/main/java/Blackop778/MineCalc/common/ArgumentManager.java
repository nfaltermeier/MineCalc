package Blackop778.MineCalc.common;

import javax.annotation.Nullable;

public class ArgumentManager
{
	private Argument args;
	private int inIndex;

	public ArgumentManager()
	{
		args = new String[0];
		inIndex = 0;
	}
	
	public void digest(String math)
	{
		
	}
	
	public void modifyOthersImportance(Argument notChanged, double amountChanged)
	{
		
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

	public static Argument[] extendArray(String[] array)
	{
		return extendArray(array, 1);
	}

	public static Argument[] extendArray(Argument[] array, int lengthToExtend)
	{
		Argument[] newArray = new Argument[array.length + lengthToExtend];
		for(int i = 0; i < array.length; i++)
		{
			newArray[i] = array[i];
		}

		return newArray;
	}
}
