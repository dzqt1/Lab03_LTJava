package com.solipsism.server.network;

import java.net.Socket;
import java.util.UUID;

import com.solipsism.server.model.*;
import com.solipsism.server.service.*;
import com.solipsism.shared.model.DataPackage;

import java.io.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private UserService userService = new UserService();
    private UUID currentUser = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                DataPackage dataPackage = (DataPackage) in.readObject();
                handlePackage(dataPackage);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (currentUser != null) {
                    handleLogout();
                }
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handlePackage(DataPackage dataPackage) {
        switch (dataPackage.getType()) {
            case NetworkProtocol.LOGIN:
                handleLogin(dataPackage.getContent());
                break;
            case NetworkProtocol.LOGOUT:
                handleLogout();
                currentUser = null;
                break;
            case NetworkProtocol.REGISTER:
                System.out.println("Client requested registration");
                handleRegister(dataPackage.getContent());
                break;
            default:
                System.out.println("Unknown package type: " + dataPackage.getType());
        }
    }

    // REGISTER //
    private void handleRegister(String registerInfo) {
        String[] parts = registerInfo.split(",");
        if (parts.length != 2) {
            DataPackage response = new DataPackage(
                NetworkProtocol.REGISTER_FAILURE, 
                null, 
                null, 
                "Invalid registration format. Expected: username,password"
            );
            sendPackage(response);
            return;
        }
        String username = parts[0];
        String password = parts[1];
       
        if (userService.register(username, password)) {
            DataPackage response = new DataPackage(
                NetworkProtocol.REGISTER_SUCCESS, 
                null, 
                null,
                "Registration successful. You can now log in."
            );
            sendPackage(response);
        } else {
            DataPackage response = new DataPackage(
                NetworkProtocol.REGISTER_FAILURE, 
                null, 
                null, 
                "Username already exists"
            );
            sendPackage(response);
        }
    }

    // LOGIN //
    private void handleLogin(String loginInfo) {
        String[] parts = loginInfo.split(",");
        if (parts.length != 2) {
            DataPackage response = new DataPackage(
                NetworkProtocol.LOGIN_FAILURE, 
                null, 
                null, 
                "Invalid login format. Expected: username,password"
            );
            sendPackage(response);
            return;
        }
        String username = parts[0];
        String password = parts[1];
        User user = userService.login(username, password);
        if (user != null) {
            currentUser = user.getId();
            DataPackage response = new DataPackage(
                NetworkProtocol.LOGIN_SUCCESS, 
                null, 
                null, 
                user.getId() + "," + user.getUsername()
            );
            sendPackage(response);
        } else {
            DataPackage response = new DataPackage(
                NetworkProtocol.LOGIN_FAILURE, 
                null, 
                null, 
                "Invalid username or password"
            );
            sendPackage(response);
        }
    }

    // LOGOUT //
    private void handleLogout() {
        if (currentUser != null) {
            User user = userService.getUserByUsername(currentUser.toString());
            if (user != null) {
                userService.logout(user.getUsername());
            }
        }
    }

    private void sendPackage(DataPackage dataPackage) {
        try {
            out.writeObject(dataPackage);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
