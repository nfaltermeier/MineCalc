package Blackop778.MineCalc.common;

public abstract class ArgumentManagerTest {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
	Functions.addFunctions();
	if (false) {
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
	} else {
	    diagnostic();
	}
    }

    public static void diagnostic() {
	try {
	    ArgumentManager m = new ArgumentManager(false);
	    m.digest("1+1");
	    System.out.println("Expected: 2 Recieved: " + String.valueOf(m.evaluate()));
	    m.clear();
	    m.digest("36/-2");
	    System.out.println("Expected: 6 Recieved: " + String.valueOf(m.evaluate()));
	    m.clear();
	    m.digest("5*(1+1)");
	    System.out.println("Expected: 10 Recieved: " + String.valueOf(m.evaluate()));
	    m.clear();
	    m.digest("10*((6/3)/1)");
	    System.out.println("Expected: 20 Recieved: " + String.valueOf(m.evaluate()));
	    m.clear();
	    m.digest("5*(1+1-(6/2))");
	    System.out.println("Expected: 5 Recieved: " + String.valueOf(m.evaluate()));
	    m.clear();
	    m.digest("6*6/-2+((-1*1)+1+(1*-1))*3");
	    System.out.println("Expected: 15 Recieved: " + String.valueOf(m.evaluate()));
	    m.clear();
	} catch (CalcExceptions e) {
	    e.printStackTrace();
	}
    }
}
