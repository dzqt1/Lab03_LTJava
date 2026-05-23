/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.solipsism.server;

import java.net.ServerSocket;

import com.solipsism.server.network.ClientHandler;

/**
 *
 * @author Nhan Vo
 */
public class Server {

    public static void main(String[] args) {
        int port = 8080;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port + "...");

            while (true) {
                // Wait for a client to connect
                var clientSocket = serverSocket.accept();
                
                new ClientHandler(clientSocket).start();

                if (Thread.currentThread().isInterrupted()) {
                    break; // Exit the loop if needed (e.g., on shutdown signal)
                }
            }
            
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
