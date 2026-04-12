package command.commands;

import command.Command;
import manager.CollectionManager;

public class ClearCommand implements Command {
    private final CollectionManager cm;

    public ClearCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public void execute(String[] args) {
        cm.clear();
        System.out.println("Collection cleared.");
    }

    @Override
    public String description() {
        return "clear the collection";
    }
}