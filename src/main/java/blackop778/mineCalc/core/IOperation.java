package blackop778.mineCalc.core;

public interface IOperation {
    /**
     * Gets the String(s) that indicates this operation
     */
    public abstract String[] getOperators();

    /**
     * Gets the Order of Operations importance of this function. Should only be
     * between or equal to 1 through 7. Default recommended is 5.
     * 
     * 1 - after addition and subtraction
     * 
     * 3 - after multiplication and division but before addition
     * 
     * 5 - after parenthesis and exponents but before multiplication
     */
    public abstract int getImportance();
}
