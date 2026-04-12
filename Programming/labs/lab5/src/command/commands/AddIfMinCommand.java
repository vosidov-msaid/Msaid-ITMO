package command.commands;

import command.Command;
import command.SpaceMarineBuilder;
import io.InputReader;
import manager.CollectionManager;
import model.SpaceMarine;

public class AddIfMinCommand implements Command {
    private final CollectionManager cm;
    private final InputReader reader;

    public AddIfMinCommand(CollectionManager cm, InputReader reader) {
        this.cm = cm;
        this.reader = reader;
    }

    @Override
    public void execute(String[] args) {
        try {
            SpaceMarine sm = new SpaceMarineBuilder(reader).build(cm.generateId());
            System.out.println(cm.addIfMin(sm) ? "Added." : "Not added.");
        } catch (Exception e) {
            System.out.println("add_if_min failed: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "add_if_min {element} - add a new element if its value is less than the smallest element in the collection.";
    }
}