/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.solipsism.client;

import java.io.*;
import java.net.Socket;

import com.solipsism.client.view.Login;

/**
 *
 * @author Nhan Vo
 */
public class Client {

    public Client() {
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        //new Client();

        String host = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(host, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Read welcome message from the server
            String serverMessage = in.readLine();
            System.out.println("Server: " + serverMessage);

            while (true) {
                // Read user input from the console
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter message to send to server (type 'exit' to quit): ");
                String message = userInput.readLine();

                // Send message to the server
                out.println(message);

                // Read response from the server
                String response = in.readLine();
                System.out.println("Server: " + response);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
