package com.solipsism.client.network;

import com.solipsism.shared.model.DataPackage;

public interface MessageListener {
    void onMessageReceived(DataPackage dataPackage);
    void onConnectionClosed();
}
