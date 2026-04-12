package command.commands;

import command.Command;
import manager.CollectionManager;

public class ShuffleCommand implements Command {
    private final CollectionManager cm;
    
    public ShuffleCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public void execute(String[] args) {
        cm.shuffle();
        System.out.println("Shuffled.");
    }

    @Override
    public String description() {
        return "randomly shuffle collection";
    }
}