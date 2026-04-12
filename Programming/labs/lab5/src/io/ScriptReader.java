package io;

import java.io.*;

public class ScriptReader implements InputReader, AutoCloseable {
    private final BufferedReader reader;

    public ScriptReader(String filePath) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public boolean isInteractive() {
        return false;
    }

    public void close() throws IOException {
        reader.close();
    }
}