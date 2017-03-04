package Blackop778.MineCalc.core.standAlone;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements ICommandManager {
    private ArrayList<ICommand> commands;

    public CommandManager() {
	commands = new ArrayList<ICommand>();
	commands.add(new Help());
    }

    @Override
    public void addCommand(ICommand toAdd) {
	commands.add(toAdd);
    }

    @Override
    public ICommand get(int index) {
	return commands.get(index);
    }

    @Override
    public List<ICommand> getWhole() {
	return new ArrayList<ICommand>(commands);
    }

    @Override
    public String processInput(String[] args) {
	// TODO Auto-generated method stub
	return null;
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
	public String execute(String[] input) {
	    List<ICommand> cmds = getWhole();
	    String toOutput = "Available commands:";
	    for (ICommand cmd : cmds) {
		toOutput += "\n" + cmd.getUsage();
	    }

	    return toOutput;
	}

    }
}
