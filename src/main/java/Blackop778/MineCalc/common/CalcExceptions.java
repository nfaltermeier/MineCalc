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

    public static class OperatorException extends CalcExceptions {

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

    /**
     * Not really an exception but a way to implement the old fancy remainders
     * functionality
     */
    public static class FancyRemainderException extends CalcExceptions {
	public int numerator;
	public int denominator;

	public FancyRemainderException(int numerator, int denominator) {
	    this.numerator = numerator;
	    this.denominator = denominator;
	}
    }

    public static class AllStandinsUsedException extends CalcExceptions {
	public AllStandinsUsedException(String standins) {
	    super(standins);
	}
    }
}
