package blackop778.mineCalc.core.standAlone;

import blackop778.mineCalc.core.standAlone.commands.CalculateSA;
import blackop778.mineCalc.core.standAlone.commands.FancyRemainders;
import blackop778.mineCalc.core.standAlone.commands.ReturnInput;
import blackop778.mineCalc.core.standAlone.commands.Version;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Nonnull
    @Override
    public List<ICommandSA> getWhole() {
        return new ArrayList<ICommandSA>(commands);
    }

    @Nullable
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
        findCMD:
        for (ICommandSA cmd : commands) {
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

        return command.execute(args);
    }

    public class Help implements ICommandSA {

        @Nonnull
        @Override
        public String getUsage() {
            return "help <command name>";
        }

        @Nonnull
        @Override
        public String getTrigger() {
            return "help";
        }

        @Nonnull
        @Override
        public List<String> getAliases() {
            return new ArrayList<String>();
        }

        @Nonnull
        @Override
        public String execute(@Nonnull String[] args) {
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

            StringBuilder toOutput = new StringBuilder("Available commands:");
            for (ICommandSA cmd : cmds) {
                toOutput.append("\n").append(cmd.getUsage());
            }

            return toOutput.toString();
        }

        @Nonnull
        @Override
        public String getEffect() {
            return "No args - Lists commands\n    [command name] - displays info for command";
        }

    }
}
