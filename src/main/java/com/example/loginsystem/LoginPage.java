package com.example.loginsystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPage extends Application implements EventHandler<ActionEvent> {

    Button LoginButton;
    Button RegisterButton;
    Button InitializeDatabaseButton;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterPage.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 545);
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent event) {

    }


    public static void main(String[] args) {
        launch();
    }
}
