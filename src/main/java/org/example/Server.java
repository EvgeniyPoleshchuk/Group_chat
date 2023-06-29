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
    private static final String LOG_DIR = "log.txt";
    private static final String INFO_PORT_DIR = "port.txe";
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) throws IOException {
        new Server(2000);
    }

    public Server(int port) throws IOException {
        createFiles(port);
        serverSocket = new ServerSocket(port);
        Socket clientSocket = null;
        System.out.println("Server starting");
        try {
            while (true) {
                clientSocket = serverSocket.accept();
                UserList newUser = new UserList(clientSocket, this);
                clients.add(newUser);
                System.out.println(clients.size());
                new Thread(newUser).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            clientSocket.close();
            serverSocket.close();

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
