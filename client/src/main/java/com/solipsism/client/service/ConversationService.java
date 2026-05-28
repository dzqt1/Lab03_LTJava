package com.solipsism.client.service;

import com.solipsism.client.network.*;
import com.solipsism.client.model.Conversation;
import com.solipsism.shared.model.DataPackage;
import java.io.IOException;
import java.util.*;

public class ConversationService implements MessageListener {
    private ClientSocket clientSocket;
    private ServerListener serverListener;

    public ConversationService(ClientSocket clientSocket) {
    }

    @Override
    public void onMessageReceived(DataPackage dataPackage) {
    }

    @Override
    public void onConnectionClosed() {
    }

    public List<Conversation> getConversations() {

        List<Conversation> conversations = new ArrayList<>();
        Conversation conv1 = new Conversation(UUID.randomUUID(), "General Chat", new java.sql.Timestamp(System.currentTimeMillis()), UUID.randomUUID(), "GROUP");
        Conversation conv2 = new Conversation(UUID.randomUUID(), "Project Discussion", new java.sql.Timestamp(System.currentTimeMillis()), UUID.randomUUID(), "GROUP");
        Conversation conv3 = new Conversation(UUID.randomUUID(), "Private Chat with Alice", new java.sql.Timestamp(System.currentTimeMillis()), UUID.randomUUID(), "PRIVATE");
        conversations.add(conv1);
        conversations.add(conv2);
        conversations.add(conv3);
        return conversations;
        
        /*
        DataPackage request = new DataPackage(NetworkProtocol.CONVERSATION_LIST, null, null, null);
        try {
            clientSocket.sendPackage(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
        */
    }
}
