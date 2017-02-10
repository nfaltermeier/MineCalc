package Blackop778.MineCalc.common;

public abstract class ArgumentManagerTest {
    public static void main(String[] args) {
	Operations.addOperations();
	Calculator.evaluate("6*6/-2+((-1*1)+1+(1*-1))*3", false);
    }

    public static void diagnostic() {

    }
}
