package blackop778.mineCalc.core;

public interface IUnaryOperation extends IOperation {
    double evaluateFunction(double input) throws CalcExceptions;
}
