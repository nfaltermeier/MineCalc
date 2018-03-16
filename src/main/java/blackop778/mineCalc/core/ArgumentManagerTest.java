package blackop778.mineCalc.core;

import blackop778.mineCalc.core.CalcExceptions.*;

import java.util.ArrayList;

public abstract class ArgumentManagerTest {
    public static final boolean DEBUG = false;

    public static void main(String[] args) {
        ArrayList<Test> tests = new ArrayList<Test>();
        addTests(tests);
        diagnostic(tests);
    }

    public static void diagnostic(ArrayList<Test> tests) {
        int fails = 0;
        for (int i = 0; i < tests.size(); i++) {
            if (!tests.get(i).test(i, DEBUG)) {
                fails++;
            }
        }

        if (fails == 0) {
            System.out.println("All tests succeeded");
        } else {
            System.out.println(fails + " test" + (fails == 1 ? "" : "s") + " failed");
        }
    }

    public static void addTests(ArrayList<Test> tests) {
        tests.add(new Test("3+(4+3)*6", true, 45.0));
        tests.add(new Test("(6*6)/--2+((-1*1)+1+(1*-1))*3", true, 3.0));
        tests.add(new Test("2+3*4", true, 14.0));
        tests.add(new Test("2+3*4", false, 20.0));
        tests.add(new Test("1.25*4/2", true, 2.5));
        tests.add(new Test("5*(1+1-(6/2))", true, -5.0));
        tests.add(new Test("5--6", false, 11.0));
        tests.add(new Test("5y6", false, new OperatorException()));
        tests.add(new Test("80/0", true, new DivisionException()));
        tests.add(new Test("-5/--2", true, new ImaginaryNumberException()));
        tests.add(new Test("6*l", true, new PreviousOutputException()));
        tests.add(new Test("2^3^2", true, 64.0));
        tests.add(new Test("3^(2+1)", true, 27.0));
        tests.add(new Test("0/5", true, 0.0));
        tests.add(new Test("2^-2", true, .25));
        tests.add(new Test("(1/2)^(1+1)", true, .25));
        tests.add(new Test("-8^(1/2)", true, new ImaginaryNumberException()));
        tests.add(new Test("-8^(1/3)", true, -2.0));
        tests.add(new Test("9/---2", true, (1 / 3.0)));
        tests.add(new Test(".25+6..75", true, new MultiplePointsException()));
        tests.add(new Test("8-9.0.4", true, new MultiplePointsException()));
        tests.add(new Test("8*", true, new UsageException()));
        tests.add(new Test("r%4", true, new InvalidNumberException("u")));
        tests.add(new Test("pi*1", true, Math.PI));
        tests.add(new Test("e*1", true, Math.E));
        tests.add(new Test("1+(6*7", true, new ParenthesisException(true)));
        tests.add(new Test("(9*8)", true, 72.0));
        tests.add(new Test("((8*8))-3", true, 61.0));
        tests.add(new Test("7*(5%3(", true, new ParenthesisException(true)));
        tests.add(new Test("1 + 1 + 1 - 1", true, 2.0));
        tests.add(new Test("6-u*9", true, new InvalidNumberException("u")));
        tests.add(new Test("6%5", true, new FancyRemainderException(6, 5)));
        tests.add(new Test("7%5-2", true, 0.0));
        tests.add(new Test("-(5*6)", true, -30.0));
        tests.add(new Test("4**7", true, new UsageException()));
        tests.add(new Test("5.5>>4", true, new BitwiseDecimalException(4.5)));
        tests.add(new Test("4-5--6", true, 5.0));
        tests.add(new Test("e", true, Math.E));
        tests.add(new Test("~7", true, -8.0));
        tests.add(new Test("1-~654*2", true, 1311.0));
        tests.add(new Test("1+4~", false, -6.0));
        tests.add(new Test("(6+8)4", true, new UsageException()));
    }
}
