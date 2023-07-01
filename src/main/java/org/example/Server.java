package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    private static ArrayList<UserList> clients = new ArrayList<>();
    private static ServerSocket serverSocket;
    private static final String LOG_DIR = "log.txt";
    private static final String INFO_PORT_DIR = "settings.txt";
    private static final String HOST_NAME = "localhost";


    public static void main(String[] args) throws IOException {
        new Server(2000);
    }
    public Server(int port) throws IOException {
        createFiles(port);
        serverSocket = new ServerSocket(port);
        Socket clientSocket = null;
        try {
            System.out.println("Сервер запущен");
            while (true) {
                clientSocket = serverSocket.accept();
                UserList newUser = new UserList(clientSocket, this);
                clients.add(newUser);
                new Thread(newUser).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            serverSocket.close();
        }
    }

    public void remove(UserList user) {
        clients.remove(user);
    }

    public void sendMassageAllUser(String massage) {
        for (UserList o : clients) {
            o.send(massage);
        }
    }

    public void createFiles(int portName) {
        File log = new File(LOG_DIR);
        File port = new File(INFO_PORT_DIR);
        try {
            if (log.createNewFile() && port.createNewFile()) {
                System.out.println("Успешно создано");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INFO_PORT_DIR))) {
            writer.write(HOST_NAME + " " + portName);
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
