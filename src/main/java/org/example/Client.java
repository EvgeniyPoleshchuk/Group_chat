package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {


    boolean close = true;
    public Client(String hostName, int port) {
        try (Socket socket = new Socket(hostName, port);
             Scanner inMassage = new Scanner(socket.getInputStream());
             PrintWriter out = new PrintWriter(socket.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            new Thread(() -> {
                while(close){
                    String input = inMassage.nextLine();
                    System.out.println(input);
                }
            }).start();

            while (true) {
                String outMassage = reader.readLine();
                if(outMassage.equals("exit")){
                    out.println("exit");
                    close = false;
                    break;
                }
                out.println(outMassage);
                out.flush();
            }
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}
