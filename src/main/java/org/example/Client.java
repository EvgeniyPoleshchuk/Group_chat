package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

public class Client {


    public static void main(String[] args) throws IOException {
        String param = "D:/Games/port.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(param));) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line + "\n");
            }
        }

    }
}
