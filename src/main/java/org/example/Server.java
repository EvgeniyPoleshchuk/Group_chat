package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<UserList> clients = new ArrayList<>();
    static ServerSocket serverSocket;
    public static final String LOGDIR = "D:/Games/log.txt";
    public static final String INFOPORTDIR = "D:/Games/port.txt";
    public static final String HOSTNAME = "localhost";
    public static final int portNAME = 2000;

    public static void main(String[] args) throws Exception {
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
        writer.write(HOSTNAME + " " + portNAME);
        writer.flush();


        serverSocket = new ServerSocket(portNAME);
        while (true) {
            UserList list = new UserList(serverSocket.accept());
            clients.add(list);
            list.start();
        }
    }
}
