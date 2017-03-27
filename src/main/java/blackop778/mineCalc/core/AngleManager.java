package blackop778.mineCalc.core;

public abstract class AngleManager {
    public static enum AngleType {
	DEGREE, GRADIAN, RADIAN
    }

    public static AngleType inputType;

    public static double convertInputToRadian(double angle) {
	if (inputType.equals(AngleType.DEGREE)) {
	    return angle * Math.PI / 180;
	} else if (inputType.equals(AngleType.GRADIAN)) {
	    return angle * Math.PI / 200;
	} else
	    return angle;
    }

    public static double convertRadianToInput(double angle) {
	if (inputType.equals(AngleType.DEGREE)) {
	    return angle / Math.PI * 180;
	} else if (inputType.equals(AngleType.GRADIAN)) {
	    return angle / Math.PI * 200;
	} else
	    return angle;
    }
}
