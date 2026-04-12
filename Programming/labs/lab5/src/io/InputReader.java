package io;

import java.io.*;

public interface InputReader {
    String readLine() throws IOException;
    boolean isInteractive();
}