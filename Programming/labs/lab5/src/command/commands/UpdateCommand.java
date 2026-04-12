package command.commands;

import command.Command;
import command.SpaceMarineBuilder;
import io.InputReader;
import manager.CollectionManager;
import model.SpaceMarine;

public class UpdateCommand implements Command {
    private final CollectionManager cm;
    private final InputReader reader;

    public UpdateCommand(CollectionManager cm, InputReader reader) {
        this.cm = cm;
        this.reader = reader;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: update {id}");
            return;
        }

        try {
            long id = Long.parseLong(args[0]);
            if(!cm.idExists(id)) {
                System.out.println("No element with ID " + id);
                return;
            }
            SpaceMarine updated = new SpaceMarineBuilder(reader).build(id);
            cm.updateById(id, updated);
            System.out.println("Updated ID = " + id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a number.");
        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "update {id} - update element by ID.";
    }
}