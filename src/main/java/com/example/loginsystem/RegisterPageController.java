package com.example.loginsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class RegisterPageController {
    LoginPage login = new LoginPage();
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {

    }

    public void handleRegister(ActionEvent actionEvent) {
        System.out.println("Register User!");
    }

    public void handleGoBack(ActionEvent actionEvent) throws IOException {
        login.changeScene("login-page.fxml");
    }
}