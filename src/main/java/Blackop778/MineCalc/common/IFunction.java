package Blackop778.MineCalc.common;

import Blackop778.MineCalc.common.ArgumentManager.Type;

public interface IFunction {
    /**
     * 
     * @param currentChar
     *            the char to be examined
     * @param lastType
     *            the last type we received
     * @return JUNK if not recognized else the appropriate Type
     */
    public abstract Type getType(Character currentChar, Type lastType);

    public abstract double evaluateFunction(double n1, double n2) throws CalcExceptions;

    // This should not change during runtime, ever.
    public abstract Type getHandledType();
}
