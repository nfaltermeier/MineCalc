package blackop778.mineCalc.core.standAlone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import blackop778.mineCalc.core.standAlone.commands.CalculateSA;
import blackop778.mineCalc.core.standAlone.commands.FancyRemainders;
import blackop778.mineCalc.core.standAlone.commands.ReturnInput;
import blackop778.mineCalc.core.standAlone.commands.Version;

public class CommandManager implements ICommandManagerSA {
    private ArrayList<ICommandSA> commands;

    public CommandManager() {
	commands = new ArrayList<ICommandSA>();
	add(new Version());
	add(new FancyRemainders());
	add(new ReturnInput());
	add(new CalculateSA());
	add(new Help());
    }

    @Override
    public void add(ICommandSA toAdd) {
	commands.add(toAdd);
    }

    @Override
    public ICommandSA get(int index) {
	return commands.get(index);
    }

    @Override
    public List<ICommandSA> getWhole() {
	return new ArrayList<ICommandSA>(commands);
    }

    public ICommandSA getByTriggers(String trigger) {
	for (ICommandSA cmd : commands) {
	    if (cmd.getTrigger().equals(trigger))
		return cmd;
	}

	return null;
    }

    @Override
    public String processInput(String[] args) {
	ICommandSA command = null;
	findCMD: for (ICommandSA cmd : commands) {
	    List<String> names = cmd.getAliases();
	    names.add(cmd.getTrigger());
	    for (String current : names) {
		if (current.equals(args[0])) {
		    command = cmd;
		    break findCMD;
		}
	    }
	}

	if (command == null) {
	    command = getByTriggers("help");
	}

	// Remove first arg
	args = Arrays.asList(args).subList(1, args.length).toArray(new String[0]);
	String output = command.execute(args);

	return output;
    }

    public class Help implements ICommandSA {

	@Override
	public String getUsage() {
	    return "help <command name>";
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
	public String execute(String[] args) {
	    List<ICommandSA> cmds = getWhole();
	    if (args.length != 0) {
		for (ICommandSA cmd : commands) {
		    List<String> names = cmd.getAliases();
		    names.add(cmd.getTrigger());
		    for (String current : names) {
			if (current.equals(args[0]))
			    return "Usage: " + cmd.getUsage() + "\n    " + cmd.getEffect();
		    }
		}
	    }

	    String toOutput = "Available commands:";
	    for (ICommandSA cmd : cmds) {
		toOutput += "\n" + cmd.getUsage();
	    }

	    return toOutput;
	}

	@Override
	public String getEffect() {
	    return "No args - Lists commands\n    [command name] - displays info for command";
	}

    }
}
