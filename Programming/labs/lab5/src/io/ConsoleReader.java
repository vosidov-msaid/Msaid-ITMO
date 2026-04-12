package io;

import java.io.*;

public class ConsoleReader implements InputReader {
    private final BufferedReader reader;
    
    public ConsoleReader() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public boolean isInteractive() {
        return true;
    }
}