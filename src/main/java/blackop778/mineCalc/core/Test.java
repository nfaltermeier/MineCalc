package blackop778.mineCalc.core;

public class Test {
    private final String math;
    private final boolean useOOPS;
    private final Object answer;

    public Test(String math, boolean useOOPS, Object answer) {
        this.math = math;
        this.useOOPS = useOOPS;
        this.answer = answer;
    }

    public boolean test(int index, boolean debug) {
        Object result;
        try {
            result = Calculator.evaluate(math, useOOPS, null, true);
        } catch (CalcExceptions e) {
            result = e;
        }

        boolean success;
        if (result instanceof CalcExceptions) {
            success = result.getClass().equals(answer.getClass());
        } else {
            success = result.equals(answer);
        }

        if (!success || debug) {
            /*if (result instanceof Exception)
                ((Exception) result).printStackTrace();*/
            System.out.println("Test " + (index + 1) + " result: " + (success ? "Success" : "Failure") + ". " + math
                    + " Returned: " + result + " Expected: " + answer);
        }

        return success;
    }
}
