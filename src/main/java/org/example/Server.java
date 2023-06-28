package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;


public class Server {
    private static ArrayList<UserList> clients = new ArrayList<>();
    private static ServerSocket serverSocket;
    private static final String LOGDIR = "D:/Games/log.txt";
    private static final String INFOPORTDIR = "D:/Games/port.txt";
    private static final String HOSTNAME = "localhost";


    public Server(int port) throws IOException {
        createFiles(port);
        serverSocket = new ServerSocket(port);
        System.out.println("Server starting");
        while (true) {
            UserList list = new UserList(serverSocket.accept(), this);
            clients.add(list);
            new Thread(list).start();
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

    public void createFiles(int portName) throws IOException {
        File log = new File(LOGDIR);
        File port = new File(INFOPORTDIR);
        try {
            if (log.createNewFile() && port.createNewFile()) {
                System.out.println("ok");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(INFOPORTDIR));
        writer.write(HOSTNAME + " " + portName);
        writer.flush();
        writer.close();

    }
}
