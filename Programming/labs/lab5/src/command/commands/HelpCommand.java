package command.commands;

import command.Command;
import java.util.Map;

public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args){
        System.out.println("--- Available commands ---");
        commands.forEach((name, cmd) -> {
            System.out.println(name + " - " + cmd.description());
        });
    }

    @Override
    public String description() {
        return "show available commands";
    }
}