package com.example.loginsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RegisterPageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {

    }

    public void handleRegister(ActionEvent actionEvent) {
        System.out.println("Register User!");
    }
}