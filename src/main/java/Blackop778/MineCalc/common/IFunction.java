package Blackop778.MineCalc.common;

import Blackop778.MineCalc.common.ArgumentManager.FunctionType;
import Blackop778.MineCalc.common.ArgumentManager.Type;

public interface IFunction {
    /**
     * '$' should NOT be used in any part of a valid Function
     * 
     * @param currentChar
     *            the char to be examined
     * @param lastType
     *            the last type we received
     * @return JUNK if not recognized else the appropriate Type
     */
    public abstract FunctionType getType(Character currentChar, Character lastCharacter, Type lastType);

    /**
     * 
     * @param n1
     *            The first number to process
     * @param n2
     *            The second number to process
     * @return The result of preforming the function on n1 and n2
     * @throws CalcExceptions
     *             Problems we may encounter. Should be a subclass.
     */
    public abstract double evaluateFunction(double n1, double n2) throws CalcExceptions;
}
