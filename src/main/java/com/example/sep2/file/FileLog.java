package com.example.sep2.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class FileLog {
    private final CurrentTime currentTime;
    private final File logFile;

    private static final HashMap<File, FileLog> instances = new HashMap<>();

    public FileLog(File logFile) {
        this.logFile = logFile;
        currentTime = CurrentTime.getInstance();
    }

    public synchronized static FileLog getInstance(File logFile) {
        if (!instances.containsKey(logFile)) {
            instances.put(logFile, new FileLog(logFile));
        }
        return instances.get(logFile);
    }

    private File getLogFile() {
        return logFile;
    }

    public void log(String message) throws IOException {
        try (FileWriter fileWriter = new FileWriter(getLogFile(), true);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            String logLine = currentTime.getFormattedTime() + " - " + message;
            writer.println(logLine);
        }
    }
}
