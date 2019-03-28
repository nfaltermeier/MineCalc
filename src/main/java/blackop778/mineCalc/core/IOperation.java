package blackop778.mineCalc.core;

public interface IOperation {
    /**
     * Gets the String(s) that indicates this operation
     */
    String[] getOperators();

    /**
     * Gets the Order of Operations importance of this function. Should only be
     * between or equal to 1 through 7. Default recommended is 5.
     * <p>
     * 1 - after addition and subtraction
     * <p>
     * 3 - after multiplication and division but before addition
     * <p>
     * 5 - after parenthesis and exponents but before multiplication
     */
    int getPrecedence();

    // TODO: Implement
    /*
     * Returns a string explaining how the operation is used
     */
    // String getUsage();
}
