package command.commands;

import command.Command;

public class ExitCommand implements Command {
    @Override
    public void execute(String[] args) {
        String goodbye = """
             ____                 _ _                
            / ___| ___   ___   __| | |__  _   _  ___ 
           | |  _ / _ \\ / _ \\ / _` | '_ \\| | | |/ _ \\
           | |_| | (_) | (_) | (_| | |_) | |_| |  __/
            \\____|\\___/ \\___/ \\__,_|_.__/ \\__, |\\___|
                                               |
                                           |___/     
            """;
        System.out.println(goodbye);
        System.exit(1);
    }

    @Override
    public String description() {
        return "quit without saving";
    }
}