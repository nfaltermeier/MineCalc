package Blackop778.MineCalc.server;

public class LastNumber implements ILastNumber {
    private double lastNumber;

    @Override
    public double getLastNumber() {
	return lastNumber;
    }

    @Override
    public void setLastNumber(double num) {
	this.lastNumber = num;
    }
}
