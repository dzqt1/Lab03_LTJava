package com.solipsism.server.network;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    ConcurrentHashMap<UUID, ClientHandler> clients = new ConcurrentHashMap<>();
}