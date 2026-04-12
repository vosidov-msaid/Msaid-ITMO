package command.commands;

import command.Command;
import manager.CollectionManager;

public class PrintFieldDescendingHealthCommand implements Command {
    private final CollectionManager cm;

    public PrintFieldDescendingHealthCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public void execute(String[] args) {
        cm.getHealthDescending().forEach(System.out::println);
    }

    @Override
    public String description() {
        return "print health desc";
    }
}