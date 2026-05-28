package com.solipsism.client.viewModel;

import com.solipsism.client.model.Conversation;
import com.solipsism.client.service.ConversationService;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class ConversationViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ConversationService convService;

    private List<Conversation> conversations = new ArrayList<>();

    public ConversationViewModel() {
        convService = new ConversationService(null);
        conversations = convService.getConversations();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void addConversation(Conversation conversation) {
        if (conversation == null) return;
        List<Conversation> oldConversations = new ArrayList<>(conversations);
        conversations.add(conversation);
        support.firePropertyChange("conversations", oldConversations, conversations);
    }

    public List<Conversation> getConversations() {
        return conversations;
    }
}