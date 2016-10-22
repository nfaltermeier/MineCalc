package Blackop778.MineCalc.common;

public abstract class ArgumentManagerTest {
    public static void main(String[] args) {
	Functions.addFunctions();
	ArgumentManager manager = new ArgumentManager(false);
	manager.digest("6*6/-2+((-1*1)+1+(1*-1))*3");
	double answer = -9999;
	try {
	    answer = manager.evaluate();
	} catch (CalcExceptions e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println(answer);
	System.exit(0);
    }
}
