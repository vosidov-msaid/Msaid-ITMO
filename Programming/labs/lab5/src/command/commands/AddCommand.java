package command.commands;

import command.Command;
import command.SpaceMarineBuilder;
import io.InputReader;
import manager.CollectionManager;
import model.SpaceMarine;

public class AddCommand implements Command {
    private CollectionManager cm;
    private final InputReader reader;

    public AddCommand(CollectionManager cm, InputReader reader) {
        this.cm = cm;
        this.reader = reader;
    }

    @Override
    public void execute(String[] args) {
        try {
            SpaceMarine sm = new SpaceMarineBuilder(reader).build(cm.generateId());
            cm.add(sm);
            System.out.println("Added: " + sm.getName());
        } catch (Exception e) {
            System.out.println("Add failed: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "add {element} - add a new element.";
    }
}