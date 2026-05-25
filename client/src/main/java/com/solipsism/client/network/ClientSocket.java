package com.solipsism.client.network;

import java.net.Socket;

import com.solipsism.shared.model.DataPackage;

import java.io.*;

public class ClientSocket {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private volatile boolean connected = false;

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        connected = true;
    }

    public void sendPackage(DataPackage data) throws IOException {
        if (connected) {
            out.writeObject(data);
            out.flush();
        } else {
            throw new IOException("Not connected to server");
        }
    }

    public DataPackage receivePackage() throws IOException, ClassNotFoundException {
        if (connected) {
            return (DataPackage) in.readObject();
        } else {
            throw new IOException("Not connected to server");
        }
    }

    public void disconnect() throws IOException {
        connected = false;
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null) socket.close();
    }

    public boolean isConnected() {
        return connected && socket.isConnected();
    }
}
