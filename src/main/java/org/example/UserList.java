package org.example;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserList implements Runnable{
    private Socket socket;
    private Scanner massage;
    private PrintWriter printWriter;
    private Logger logger = Logger.getInstance();
    private Server server;

    public UserList(Socket user,Server server) {
        try {
            this.server = server;
            this.socket = user;
            this.massage = new Scanner(socket.getInputStream());
            this.printWriter = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            server.sendMassageAllUser("Server : new user is connect");
            logger.LogWriter("Server : ", "new user is connect " + socket.getLocalAddress().toString());
            while (true) {
                if (massage.hasNext()) {
                    String userMassage = massage.nextLine();
                    if (userMassage.equals("exit")) {
                        break;
                    }
                    server.sendMassageAllUser(userMassage);
                    logger.LogWriter("user",userMassage);
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }finally {
          this.close();
        }
    }
    public void send(String massage) {
        printWriter.println(massage);
    }
    public void close(){
        try {
            server.remove(this);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
