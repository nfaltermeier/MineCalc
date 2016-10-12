package Blackop778.MineCalc.common;

public class ArgumentManagerTest {
    public static void main(String[] args) {
	ArgumentManager manager = new ArgumentManager(false);
	manager.digest("4+8/3+(45*3+1+(2-1))+1-7");
	System.exit(0);
    }
}
