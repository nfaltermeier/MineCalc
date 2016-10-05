package Blackop778.MineCalc.common;

import Blackop778.MineCalc.MineCalc;
import Blackop778.MineCalc.common.ArgumentManager.Type;

public abstract class Functions
{
	static void addFunctions()
	{
		MineCalc.functions.add(new IFunction(){
			@Override
			public Type getType(Character currentChar, Type lastType) {
				if(currentChar.equals('+'))
					return Type.ADDITION;
				return Type.JUNK;
			}

			@Override
			public double evaluateFunction(double n1, double n2) throws CalcExceptions {
				return n1 + n2;
			}

                        @Override
                        public Type getHandledType()
                        {
                            return Type.ADDITION;
                        }
		});
		
		MineCalc.functions.add(new IFunction(){
			@Override
			public Type getType(Character currentChar, Type lastType) {
				if(currentChar.equals('-'))
				{
					if(lastType.equals(Type.DIVISION))
						return Type.EXPONENTROOT;
					return Type.DIVISION;
				}
					
				return Type.JUNK;
			}

			@Override
			public double evaluateFunction(double n1, double n2) throws CalcExceptions {
				return n1 - n2;
			}

                        @Override
                        public Type getHandledType()
                        {
                            return Type.SUBTRACTION;
                        }
		});
		
		MineCalc.functions.add(new IFunction(){
			@Override
			public Type getType(Character currentChar, Type lastType) {
				if(currentChar.equals('*') || currentChar.toString().equalsIgnoreCase("x"))
					return Type.MULTIPLICATION;
				return Type.JUNK;
			}

			@Override
			public double evaluateFunction(double n1, double n2) throws CalcExceptions {
				return n1 * n2;
			}

                        @Override
                        public Type getHandledType()
                        {
                            return Type.MULTIPLICATION;
                        }
		});
		
		MineCalc.functions.add(new IFunction(){
			@Override
			public Type getType(Character currentChar, Type lastType) {
				if(currentChar.equals('/'))
					return Type.DIVISION;
				return Type.JUNK;
			}

			@Override
			public double evaluateFunction(double n1, double n2) throws CalcExceptions {
				if(n2 == 0)
					throw new CalcExceptions.DivisionException();
				return n1 / n2;
			}

                        @Override
                        public Type getHandledType()
                        {
                            return Type.DIVISION;
                        }
		});
		
		MineCalc.functions.add(new IFunction(){
			@Override
			public Type getType(Character currentChar, Type lastType) {
				if(currentChar.equals('%'))
					return Type.MODULO;
				return Type.JUNK;
			}

			@Override
			public double evaluateFunction(double n1, double n2) throws CalcExceptions {
				if(n2 == 0)
					throw new CalcExceptions.DivisionException();
				return n1 % n2;
			}

                        @Override
                        public Type getHandledType()
                        {
                            return Type.MODULO;
                        }
		});
		
		MineCalc.functions.add(new IFunction(){
			@Override
			public Type getType(Character currentChar, Type lastType) {
				if(currentChar.equals('^'))
					return Type.EXPONENT;
				return Type.JUNK;
			}

			@Override
			public double evaluateFunction(double n1, double n2) throws CalcExceptions {
				return Math.pow(n1,n2);
			}
		});
	}
}
