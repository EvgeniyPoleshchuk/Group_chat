package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
    private static ArrayList<UserList> clients = new ArrayList<>();
    private static ServerSocket serverSocket;
    private static final String LOG_DIR = "C:/test/log.txt";
    private static final String INFO_PORT_DIR = "C:/test/port.txt";
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {
        new Server(2000);
    }
    public Server(int port)  {
        try {
            createFiles(port);
            serverSocket = new ServerSocket(port);
            System.out.println("Server starting");
            while (true) {
                System.out.println(clients.size());
                UserList newUser = new UserList(serverSocket.accept(), this);
                clients.add(newUser);
                new Thread(newUser).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void remove(UserList user) {
        clients.remove(user);
        System.out.println(clients.size());
    }

    public void sendMassageAllUser(String massage) {
        for (UserList o : clients) {
            o.send(massage);
        }
    }

    public void createFiles(int portName) throws IOException {
        File log = new File(LOG_DIR);
        File port = new File(INFO_PORT_DIR);
        File dir = new File("C:/test");
        dir.mkdir();
        try {
            if (log.createNewFile() && port.createNewFile()) {
                System.out.println("ok");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(INFO_PORT_DIR));
        writer.write(HOSTNAME + " " + portName);
        writer.flush();
        writer.close();

    }
}
