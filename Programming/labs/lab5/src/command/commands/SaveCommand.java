package command.commands;

import command.Command;
import manager.CollectionManager;

public class SaveCommand implements Command {
    private final CollectionManager cm;

    public SaveCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public void execute(String[] args) {
        cm.save();
    }

    @Override
    public String description() {
        return "save collection to file.";
    }
}