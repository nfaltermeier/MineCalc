package Blackop778.MineCalc.common;

@SuppressWarnings("serial")
public class CalcExceptions extends Exception {
    public static class ImaginaryNumberException extends CalcExceptions {

    }

    public static class DivisionException extends CalcExceptions {

    }

    public static class SymbolException extends CalcExceptions {

    }

    public static class PreviousOutputException extends CalcExceptions {

    }

    // It is encouraged to add a message because that will be displayed to the
    // user for these Exceptions
    public static class CustomFunctionException extends CalcExceptions {

    }
}
