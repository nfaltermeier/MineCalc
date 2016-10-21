package Blackop778.MineCalc.common;

public abstract class ArgumentManagerTest {
    public static void main(String[] args) {
	Functions.addFunctions();
	ArgumentManager manager = new ArgumentManager(false);
	manager.digest("36/-2");
	double answer = 0;
	try {
	    answer = manager.evaluate();
	} catch (CalcExceptions e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.exit(0);
    }
}
