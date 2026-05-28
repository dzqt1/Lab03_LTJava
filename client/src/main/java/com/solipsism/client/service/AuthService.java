package com.solipsism.client.service;

import com.solipsism.client.model.User;
import com.solipsism.client.network.*;
import com.solipsism.shared.model.DataPackage;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class AuthService implements MessageListener {
    private ClientSocket clientSocket;
    private List<AuthListener> listeners = new ArrayList<>();
    private ServerListener serverListener;

    public AuthService() {
    }

    @Override
    public void onMessageReceived(DataPackage dataPackage) {
        String type = dataPackage.getType();

        switch (type) {
            case NetworkProtocol.LOGIN_SUCCESS:
                handleLoginSuccess(dataPackage);
                break;
            case NetworkProtocol.LOGIN_FAILURE:
                handleLoginFailure(dataPackage);
                break;
            case NetworkProtocol.REGISTER_SUCCESS:
                handleRegisterSuccess(dataPackage);
                break;
            case NetworkProtocol.REGISTER_FAILURE:
                handleRegisterFailure(dataPackage);
                break;
            default:
                System.out.println("Unknown package type: " + type);
        }
    }

    @Override
    public void onConnectionClosed() {
        notifyConnectionClosed();
    }

    public void connect(String host, int port) throws IOException {
        clientSocket = new ClientSocket();
        clientSocket.connect(host, port);

        serverListener = new ServerListener(clientSocket, this); 
        serverListener.start();
    }

    public void login(String username, String password) {
        DataPackage loginPackage = new DataPackage(NetworkProtocol.LOGIN, null, null, username + "," + password);
        try {
            clientSocket.sendPackage(loginPackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLoginSuccess(DataPackage data) {
        try { 
            String[] parts = data.getContent().split(",");
            UUID userId = UUID.fromString(parts[0]);
            String username = parts[1];

            SessionService.getInstance().login(userId, username);

            notifyLoginSuccess(userId, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLoginFailure(DataPackage data) {
        String message = data.getContent();
        notifyLoginFailure(message);
    }

    public void logout() {
        try {
            UUID userId = SessionService.getInstance().getCurrentUserId();
            
            if (clientSocket != null) {
                DataPackage logoutPackage = new DataPackage(NetworkProtocol.LOGOUT, null, userId, null);
                try {
                    clientSocket.sendPackage(logoutPackage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (serverListener != null) {
                serverListener.stopListening();
            }

            if (clientSocket != null) {
                try {
                    clientSocket.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SessionService.getInstance().logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(String username, String password) {
        DataPackage registerPackage = new DataPackage(NetworkProtocol.REGISTER, null, null, username + "," + password);
        try {
            clientSocket.sendPackage(registerPackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRegisterSuccess(DataPackage data) {
        notifyRegisterSuccess();
    }

    public void handleRegisterFailure(DataPackage data) {
        String message = data.getContent();
        notifyRegisterFailure(message);
    }

    public interface AuthListener {
        void onSuccess(UUID userId, String username);
        void onFailure(String message);
        void onConnectionClosed();
    }

    public void addListener(AuthListener listener) {
        listeners.add(listener);
    }

    public void removeListener(AuthListener listener) {
        listeners.remove(listener);
    }

    private void notifyLoginSuccess(UUID userId, String username) {
        for (AuthListener listener : listeners) {
            listener.onSuccess(userId, username);
        }
    }

    private void notifyLoginFailure(String message) {
        for (AuthListener listener : listeners) {
            listener.onFailure(message);
        }
    }

    private void notifyRegisterSuccess() {
        for (AuthListener listener : listeners) {
            listener.onSuccess(null, null);
        }
    }

    private void notifyRegisterFailure(String message) {
        for (AuthListener listener : listeners) {
            listener.onFailure(message);
        }
    }

    private void notifyConnectionClosed() {
        for (AuthListener listener : listeners) {
            listener.onConnectionClosed();
        }
    }

}
