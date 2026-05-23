package com.solipsism.client.api;

import java.util.UUID;
import java.util.List;
import java.net.URI;
import java.net.http.*;
import com.google.gson.Gson;

import com.solipsism.client.model.User;

public class UserApi {
    private final HttpClient httpClient;
    private final Gson gson = new Gson();
    private final String baseUrl;
    
    public UserApi(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.baseUrl = baseUrl;
    }

    public User login(String username, String password) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/users/login"))
                .POST(HttpRequest.BodyPublishers.ofString(
                    String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password)
                ))
                .header("Content-Type", "application/json")
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void logout(String username) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/users/logout"))
                .POST(HttpRequest.BodyPublishers.ofString(
                    String.format("{\"username\":\"%s\"}", username)
                ))
                .header("Content-Type", "application/json")
                .build();
            
            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}