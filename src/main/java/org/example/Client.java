package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Scanner inputMassage;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    boolean isInterrupted = true;


    public Client(String hostName, int port){

        try {
            clientSocket = new Socket(hostName,port);
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            inputMassage = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        readMassage();
        sendMassage();

        out.close();
        inputMassage.close();
        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void readMassage(){
        new Thread(() -> {
            while (isInterrupted) {
                String massage = inputMassage.nextLine();
                System.out.println(massage);
            }
        }).start();

    }
    public void sendMassage(){
        try {
            while (true){
                String massage = reader.readLine();
                if(massage.equals("exit")){
                    out.println("Пользователь сьебал с чата");
                    isInterrupted= false;
                    reader.close();
                    break;
                }
                out.println(massage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
