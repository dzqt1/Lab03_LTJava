/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.solipsism.client;

import java.io.*;
import java.net.Socket;

import javax.security.auth.login.LoginContext;

import com.solipsism.client.view.*;

/**
 *
 * @author Nhan Vo
 */
public class Client {

    public Client() {
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        new Client();
    }
}
