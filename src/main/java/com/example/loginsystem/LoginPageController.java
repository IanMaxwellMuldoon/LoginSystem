package com.example.loginsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class LoginPageController {
    LoginPage login = new LoginPage();
    @FXML
    Button LoginButton;
    @FXML
    Button RegisterButton;
    @FXML
    TextField UsernameTextField;
    @FXML
    TextField PassTextField;
    String passwordEntry;
    String usernameEntry;
    PreparedStatement prepareStatement;
    ResultSet loginResult;

    public void handleInitDatabase(ActionEvent actionEvent) {
        System.out.println("Initialize Database!!");
    }

    public void handleLogRegister(ActionEvent actionEvent) throws IOException {

        System.out.println("Go to register page");
        login.changeScene("register-page.fxml");
    }

    public void handleLogin(ActionEvent actionEvent) {

        //get login fields
        passwordEntry = PassTextField.getText();
        usernameEntry = UsernameTextField.getText();

        //set up connections
        DatabaseConnection connectU = new DatabaseConnection();
        Connection connectUser = connectU.getConnection();

        DatabaseConnection connectP = new DatabaseConnection();
        Connection connectPass = connectP.getConnection();

        //Username query
        String loginQuery = "SELECT username, password FROM users where username= ?";


        try {
            //create prepared statements
            prepareStatement = connectUser.prepareStatement(loginQuery);

            //insert parameters
            prepareStatement.setString(1, usernameEntry);

            //Execute Prepared Statement
            loginResult = prepareStatement.executeQuery();

            //get results
            if(!loginResult.isBeforeFirst()){
                System.out.println("Try Again");
            }else {
                while (loginResult.next()) {
                    if (usernameEntry.equals(loginResult.getString("username")) && passwordEntry.equals(loginResult.getString("password"))) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Try Again");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}