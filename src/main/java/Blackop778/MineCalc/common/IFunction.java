package Blackop778.MineCalc.common;

import Blackop778.MineCalc.common.ArgumentManager.Type;

public interface IFunction
{
	public abstract TypeReturn getType(char currentChar, Type lastType);

	public abstract double evaluateFunction(double n1, double n2);

	public class TypeReturn
	{
		public final Type type;
		public final IFunction function;

		public TypeReturn(Type type, IFunction function)
		{
			this.type = type;
			this.function = function;
		}
	}
}
