package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;
    private StringBuilder builder = new StringBuilder();

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    public void LogWriter(String name,String massage) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("D:/Games/log.txt",true))) {
            writer.write("[" + time.format(formatter) + "] " + name + ": " + massage + "\n");
            writer.flush();
        }
    }
}

