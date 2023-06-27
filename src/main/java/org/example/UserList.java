package org.example;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserList extends Thread {
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Logger logger = Logger.getInstance();

    public UserList(Socket user) throws Exception {
        socket = user;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream());
    }
    @Override
    public void run() {
        try {
            send("Hello : Enter your name");
            String name = bufferedReader.readLine();
            send("Hello " + name);
            while (socket.isConnected()) {
                String read = bufferedReader.readLine();
                if (read.equals("exit")) {
                    break;
                }
                logger.LogWriter(name,read);
                for (UserList e : Server.clients) {
                    e.send(name + " : " + read);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String massage) {
        printWriter.println(massage);
        printWriter.flush();
    }


}
