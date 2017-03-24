package blackop778.mineCalc.core;

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

    /**
     * It is required to add a message because that will be displayed to the
     * user for these Exceptions
     */
    public static class CustomFunctionException extends CalcExceptions {
	public CustomFunctionException(String message) {
	    super(message);
	}
    }

    /**
     * Not really an exception but a way to implement the old fancy remainders
     * functionality
     */
    public static class FancyRemainderException extends CalcExceptions {
	public double numerator;
	public double denominator;

	public FancyRemainderException(double numbers, double numbers2) {
	    this.numerator = numbers;
	    this.denominator = numbers2;
	}
    }

    public static class AllStandinsUsedException extends CalcExceptions {
	public AllStandinsUsedException(String standins) {
	    super(standins);
	}
    }

    public static class MultiplePointsException extends CalcExceptions {

    }

    public static class UsageException extends CalcExceptions {

    }

    public static class InvalidNumberException extends CalcExceptions {
	public InvalidNumberException(String message) {
	    super(message);
	}
    }

    public static class ParenthesisException extends CalcExceptions {
	public final boolean tooMany;

	public ParenthesisException(boolean tooMany) {
	    this.tooMany = tooMany;
	}
    }

    public static class UnaryUsageException extends CalcExceptions {

    }
}
