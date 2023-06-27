package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String param = "D:/Games/port.txt";
        String[] connct = new String[1];
        try (BufferedReader reader = new BufferedReader(new FileReader(param))) {
            String line;
            while ((line = reader.readLine()) != null) {
                connct = line.split(" ");
            }
        }
        try (Socket clientSocket = new Socket(connct[0], Integer.parseInt(connct[1]));
             PrintWriter out = new
                     PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new
                     InputStreamReader(clientSocket.getInputStream()))) {

            Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        String read = in.readLine();
                        System.out.println(read);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            while (true) {
                String word = scanner.nextLine();
                if (word.equals("exit")) {
                    break;
                }
                out.println(word);
            }
        }
    }
}

