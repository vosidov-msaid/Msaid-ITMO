package command.commands;

import command.Command;
import manager.CollectionManager;

public class RemoveByIdCommand implements Command {
    private final CollectionManager cm;

    public RemoveByIdCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: remove_by_id <id>");
            return;
        }

        try {
            long id = Long.parseLong(args[0]);
            System.out.println(cm.removeById(id) ? "Removed id=" + id : "ID not found: id=" + id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a number");
        }
    }

    @Override
    public String description() {
        return "remove_by_id <id> - remove element by id";
    }
}