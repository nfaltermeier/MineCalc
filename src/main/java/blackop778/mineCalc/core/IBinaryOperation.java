package blackop778.mineCalc.core;

public interface IBinaryOperation extends IOperation {
    /**
     * 
     * @return The result of performing the function on the math
     * @throws CalcExceptions
     *             Problems we may encounter. Should be a subclass.
     */
    public abstract double evaluateFunction(double numbers, double numbers2) throws CalcExceptions;
}
