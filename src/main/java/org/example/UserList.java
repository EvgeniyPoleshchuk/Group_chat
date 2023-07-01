package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class UserList implements Runnable {
    private Socket socket;
    private Scanner massage;
    private PrintWriter printWriter;
    private Logger logger = Logger.getInstance();
    private Server server;
    private String name;
    private static int userCount;

    public UserList(Socket user, Server server) {
        try {
            userCount++;
            this.server = server;
            this.socket = user;
            this.massage = new Scanner(socket.getInputStream());
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            validName(); // Проверка имени на пустую строку
                String ENTER_TO_CHAT = "Server : %s присоединился к чату";
                server.sendMassageAllUser(String.format(ENTER_TO_CHAT, name));
                logger.LogWriter(String.format(ENTER_TO_CHAT, name));
                send("Пользователей онлайн: " + userCount);
            while (true) {
                    String userMassage = massage.nextLine();
                    if (userMassage.equalsIgnoreCase("exit")) {
                        String EXIT = "Server : %s покинул чат";
                        server.sendMassageAllUser(String.format(EXIT, name));
                        logger.LogWriter(String.format(EXIT, name));
                        break;
                    } else {
                        System.out.println(name + ": " + userMassage);
                        server.sendMassageAllUser(name + ": " + userMassage);
                        logger.LogWriter(name + ": " + userMassage);
                    }
                }
            Thread.sleep(100);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.close();
        }
    }

    public void send(String massage) {
        printWriter.println(massage);
    }

    public void close() {
        server.remove(this);
        userCount--;
        server.sendMassageAllUser("Пользователей онлайн :" + userCount);
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void validName() {
        while (true) {
            send("Введите свое имя: ");
            name = massage.nextLine();
            if (!name.equals("")) {
                break;
            }
            send("Имя не может быть пустым, попробуйте еще раз");
        }

    }
}
