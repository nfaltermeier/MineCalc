package Blackop778.MineCalc.common;

public abstract class ArgumentManagerTest {
    public static void main(String[] args) {
	Operations.addOperations();
	try {
	    System.out.println(Calculator.evaluate("5%3*9", true));
	    System.out.println(Calculator.evaluate("6*6/--2+((-1*1)+1+(1*-1))*3", false));
	} catch (CalcExceptions e) {
	    e.printStackTrace();
	}
    }

    public static void diagnostic() {

    }
}
