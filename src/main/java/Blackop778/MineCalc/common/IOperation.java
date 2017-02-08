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

    /**
     * Gets the Order of Operations importance of this function.
     * 
     * 1 - after addition and subtraction
     * 
     * 3 - after multiplication and division but before addition
     * 
     * 5 - after parenthesis and exponents but before multiplication
     * 
     * 7 - before parenthesis and exponents
     */
    public abstract int getImportance();
}
