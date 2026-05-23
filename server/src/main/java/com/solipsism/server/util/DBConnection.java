package com.solipsism.server.util;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new FileNotFoundException("Unable to find database.properties");
            }
            properties.load(input);
            System.out.println("Database properties loaded successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load database properties", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
