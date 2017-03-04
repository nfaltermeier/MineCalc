package Blackop778.MineCalc.core.standAlone;

import java.util.List;

public class CalculateSA implements ICommandSA {

    @Override
    public String getUsage() {
	return "calc [number][operator][number]<operator><number>";
    }

    @Override
    public String getTrigger() {
	return "Calculate";
    }

    @Override
    public List<String> getAliases() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String execute(String[] arguments) {
	// TODO Auto-generated method stub
	return null;
    }

}
