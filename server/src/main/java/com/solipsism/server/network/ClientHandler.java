package com.solipsism.server.network;

import java.net.Socket;
import java.io.*;

public class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        System.out.println("Client connected: " + socket.getInetAddress());
    }

    @Override
    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Welcome to the server!, Type 'exit' to disconnect.");

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Received from client: " + clientMessage);
                if (clientMessage.equalsIgnoreCase("exit")) {
                    out.println("Goodbye!");
                    break;
                }
                out.println("Echo: " + clientMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
