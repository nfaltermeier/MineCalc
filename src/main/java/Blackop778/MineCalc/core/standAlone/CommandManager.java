package Blackop778.MineCalc.core.standAlone;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private ArrayList<ICommand> commands;

    public void addCommand(ICommand toAdd) {
	commands.add(toAdd);
    }

    public ICommand get(int index) {
	return commands.get(index);
    }

    public ArrayList<ICommand> getWhole() {
	return new ArrayList<ICommand>(commands);
    }

    public class Help implements ICommand {

	@Override
	public String getUsage() {
	    return "help - Displays this message";
	}

	@Override
	public String getTrigger() {
	    return "help";
	}

	@Override
	public List<String> getAliases() {
	    return new ArrayList<String>();
	}

	@Override
	public String execute(String input) {
	    getWhole();
	}

    }
}
