package com.solipsism.client.network;

import java.net.Socket;

import com.solipsism.shared.model.DataPackage;

import java.io.*;

public class ServerListener extends Thread {
    private ClientSocket clientSocket;
    private MessageListener callback;
    private volatile boolean running = true;

    public ServerListener(ClientSocket clientSocket, MessageListener callback) {
        this.clientSocket = clientSocket;
        this.callback = callback;
        setName("ServerListener");
    }

    @Override
    public void run() {
        while (running && clientSocket.isConnected()) {
            try {
                DataPackage data = clientSocket.receivePackage();
                handlePackage(data);
            } catch (IOException e) {
                callback.onConnectionClosed();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlePackage(DataPackage data) {
        String type = data.getType();

        switch (type) {
            case NetworkProtocol.LOGIN_SUCCESS:
            case NetworkProtocol.LOGIN_FAILURE:
                callback.onMessageReceived(data);
                break;
            default:
                System.out.println("Unknown package type: " + type);
        }
    }

    public void stopListening() {
        running = false;
    }
}
