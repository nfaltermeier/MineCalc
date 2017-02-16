package Blackop778.MineCalc.common;

public abstract class ArgumentManagerTest {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
	Operations.addOperations();
	if (true) {
	    diagnostic();
	} else {
	    try {
		System.out.println(Calculator.evaluate("5%3*9", true));
		System.out.println(Calculator.evaluate("6*6/--2+((-1*1)+1+(1*-1))*3", false));
	    } catch (CalcExceptions e) {
		e.printStackTrace();
	    }
	}
    }

    public static void diagnostic() {
	try {
	    System.out.println("Expected: 45 Recieved: " + Calculator.evaluate("3+(4+3)*6", true));
	    System.out.println("Expected: 3 Recieved: " + Calculator.evaluate("(6*6)/--2+((-1*1)+1+(1*-1))*3", true));
	} catch (CalcExceptions e) {
	    e.printStackTrace();
	}
    }
}
