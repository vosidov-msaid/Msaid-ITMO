package command.commands;

import command.Command;
import io.ScriptReader;
import manager.CommandManager;

import java.io.IOException;
import java.util.Deque;

public class ExecuteScriptCommand implements Command {
    private final CommandManager cm;
    private final Deque<String> scriptStack;

    public ExecuteScriptCommand(CommandManager cm, Deque<String> scriptStack) {
        this.cm = cm;
        this.scriptStack = scriptStack;
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: execute_script <file_path>");
            return;
        }

        String path = args[0];
        if(scriptStack.contains(path)) {
            System.out.println("Recursion script detected, skipping: " + path);
            return;
        }
        scriptStack.push(path);
        try (ScriptReader sr = new ScriptReader(path)) {
            System.out.println("Executing script: " + path);
            cm.runWith(sr);
        } catch (IOException e) {
            System.out.println("Cannot script: " + e.getMessage());
        } finally {
            scriptStack.pop();
        }
    }

    @Override
    public String description() {
        return "execute_script <file_path> - execute commands from a script file.";
    }
}