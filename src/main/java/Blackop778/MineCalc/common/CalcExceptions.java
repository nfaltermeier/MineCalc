package Blackop778.MineCalc.common;

@SuppressWarnings("serial")
public abstract class CalcExceptions extends Exception {
    public CalcExceptions() {
	super();
    }

    public CalcExceptions(String message) {
	super(message);
    }

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
	public CustomFunctionException(String message) {
	    super(message);
	}
    }

    public static class RecursiveLoopException extends CalcExceptions {

    }
}
