package command.commands;

import command.Command;
import manager.CollectionManager;

public class PrintAscendingCommand implements Command {
    private final CollectionManager cm;

    public PrintAscendingCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public void execute(String[] args) {
        cm.getSortedAscending().forEach(sm -> System.out.println(sm));
    }

    @Override
    public String description() {
        return "print elements in ascending order";
    }
}