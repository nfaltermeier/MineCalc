package Blackop778.MineCalc.common;

public interface IOperation {
    /**
     * Gets the String(s) that indicates this operation
     */
    public abstract String[] getOperators();

    /**
     * 
     * @return The result of performing the function on the math
     * @throws CalcExceptions
     *             Problems we may encounter. Should be a subclass.
     */
    public abstract double evaluateFunction(int num1, int num2) throws CalcExceptions;
}
