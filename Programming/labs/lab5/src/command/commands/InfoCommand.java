package command.commands;

import command.Command;
import manager.CollectionManager;

public class InfoCommand implements Command {
    private final CollectionManager cm;
    
    public InfoCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Type      : java.util.LinkedList");
        System.out.println("Init date : " + cm.getInitDate());
        System.out.println("Size      : " + cm.getCollection().size());
    }

    @Override
    public String description() {
        return "show collection info";
    }
}