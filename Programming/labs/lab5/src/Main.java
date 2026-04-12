/*
Вопросы:
1) Id
2) Add command
3) Update command
*/

import manager.*;
import io.*;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("No arguments provided. Usage: java Main <csv_file>");
            System.exit(1);
        }

        String filename = args[0];
        FileManager fileManager = new FileManager(filename);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        collectionManager.loadFromFile();

        ConsoleReader consoleReader = new ConsoleReader();
        CommandManager commandManager = new CommandManager(collectionManager, fileManager, consoleReader);
        commandManager.run();
    }
}