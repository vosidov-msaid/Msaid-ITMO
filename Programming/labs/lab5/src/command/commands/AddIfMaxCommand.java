package command.commands;

import command.Command;
import command.SpaceMarineBuilder;
import io.InputReader;
import manager.CollectionManager;
import model.SpaceMarine;

public class AddIfMaxCommand implements Command {
    private final CollectionManager cm;
    private final InputReader reader;

    public AddIfMaxCommand(CollectionManager cm, InputReader reader) {
        this.cm = cm;
        this.reader = reader;
    }

    @Override
    public void execute(String[] args) {
        try {
            SpaceMarine sm = new SpaceMarineBuilder(reader).build(cm.generateId());
            System.out.println(cm.addIfMax(sm) ? "Added." : "Not added.");
        } catch (Exception e) {
            System.out.println("add_if_max failed: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "add_if_max {element} - add a new element if its value is greater than the largest element in the collection.";
    }
}