package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    private Socket socket;
    private Scanner inMassage;
    private PrintWriter out;
    private BufferedReader outPutMassage;

    boolean END_READ_MASSAGE = true;

    public Client(String hostName, int port)  {
        try {
            socket = new Socket(hostName, port);
            inMassage = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(),true);
            outPutMassage = new BufferedReader(new InputStreamReader(System.in));

        }catch (Exception e) {
            throw new RuntimeException();
        }
        readMassage();
        sendMassage();
        try {
            outPutMassage.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void readMassage() {
        new Thread(() -> {
            while (END_READ_MASSAGE) {
                String input = inMassage.nextLine();
                System.out.println(input);
            }
            inMassage.close();
        }).start();
    }

    public void sendMassage() {
        try {
            while (true) {
                String outMassage = outPutMassage.readLine();
                if (outMassage.equals("exit")) {
                    out.println("exit");
                    END_READ_MASSAGE = false;
                    break;
                }
                out.println(outMassage);
            }
        }catch (Exception e){
            throw new RuntimeException();
        }

    }
}
