package manager;

import io.*;
import command.*;
import model.*;
import command.commands.*;

import java.io.IOException;
import java.util.*;

public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final CollectionManager cm;
    private final FileManager fm;
    private final ConsoleReader consoleReader;
    private final Deque<String> scriptStack = new ArrayDeque<>();

    public CommandManager(CollectionManager cm, FileManager fm, ConsoleReader reader) {
        this.cm = cm;
        this.fm = fm;
        this.consoleReader = reader;

        register("help", new HelpCommand(commands));
        register("info", new InfoCommand(cm));
        register("show", new ShowCommand(cm));
        register("add", new AddCommand(cm, reader));
        register("update", new UpdateCommand(cm, reader));
        register("remove_by_id", new RemoveByIdCommand(cm));
        register("clear", new ClearCommand(cm));
        register("save", new SaveCommand(cm));
        register("execute_script", new ExecuteScriptCommand(this, scriptStack));
        register("exit", new ExitCommand());
        register("add_if_max", new AddIfMaxCommand(cm, reader));
        register("add_if_min", new AddIfMinCommand(cm, reader));
        register("shuffle", new ShuffleCommand(cm));
        register("count_by_melee_weapon", new CountByMeleeWeaponCommand(cm));
        register("print_ascending", new PrintAscendingCommand(cm));
        register("print_field_descending_health", new PrintFieldDescendingHealthCommand(cm));
    }

    public void register(String name, Command cmd) {
        commands.put(name, cmd);
    }

    public void runWith(InputReader reader) {
        while (true) {
            if (reader.isInteractive()) {
                System.out.print("> ");
            }
            String line;
            try {
                line = reader.readLine();
            }
            catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
                continue;
            }
            if (line.isEmpty()) continue;
            line = line.trim();

            String[] cmdParts = line.split("\\s+", 2);
            String cmdName = cmdParts[0].toLowerCase();
            String[] args = cmdParts.length > 1 ? cmdParts[1].split("\\s+") : new String[0];

            Command cmd = commands.get(cmdName);
            if (cmd == null) {
                System.out.println("Unknown command: " + cmdName);
                continue;
            }
            else {
                try {
                    cmd.execute(args);
                }
                catch (Exception e) {
                    System.out.println("Error executing command: " + e.getMessage());
                }
            }
        }
    }

    public void run() {
        runWith(consoleReader);
    }
}