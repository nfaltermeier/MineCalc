package Blackop778.MineCalc.common;

import Blackop778.MineCalc.common.ArgumentManager.Type;

public abstract class Functions
{

	public static class Addition implements IFunction
	{
		@Override
		public Type getType(Character currentChar, Type lastType)
		{
			if(currentChar.equals('+'))
				return Type.ADDITION;
			return Type.JUNK;
		}

		@Override
		public double evaluateFunction(double n1, double n2) throws CalcExceptions
		{
			return n1 + n2;
		}
	}
}
