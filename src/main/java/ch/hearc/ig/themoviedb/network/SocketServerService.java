package ch.hearc.ig.themoviedb.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerService implements Runnable {
    private static final int PORT = 2222;

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

