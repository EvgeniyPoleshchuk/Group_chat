package org.example;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainClient {
    private static final String FILE_NAME = "settings.txt";
    public static void main(String[] args) {
        String[] connectInfo = new String[1];
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                connectInfo = line.split(" ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Client(connectInfo[0], Integer.parseInt(connectInfo[1]));
    }

}

