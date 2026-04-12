package command.commands;

import command.Command;
import manager.CollectionManager;

public class ShowCommand implements Command {
    private final CollectionManager cm;
    
    public ShowCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public void execute(String[] args) {
        if(cm.getCollection().isEmpty()) {
            System.out.println("Collection is empty.");
            return;
        }

        cm.getCollection().forEach(System.out::println);
    }

    @Override
    public String description() {
        return "show all elements";
    }
}