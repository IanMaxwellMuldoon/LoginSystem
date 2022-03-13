package com.example.loginsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class LoginPageController {
    LoginPage login = new LoginPage();

    public void handleInitDatabase(ActionEvent actionEvent) {
        System.out.println("Initialize Database!!");
    }

    public void handleLogRegister(ActionEvent actionEvent) throws IOException {

        System.out.println("Go to register page");
        login.changeScene("register-page.fxml");
    }

    public void handleLogin(ActionEvent actionEvent) {
        System.out.println("Log in");
    }
}