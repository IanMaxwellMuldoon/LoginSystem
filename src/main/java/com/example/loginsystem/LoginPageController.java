package com.example.loginsystem;

import com.example.loginsystem.Database.DatabaseConnection;
import com.example.loginsystem.Database.ScriptRunner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class LoginPageController {
    LoginPage login = new LoginPage();
    @FXML
    Button LoginButton;
    @FXML
    Button RegisterButton;
    @FXML
    Button queryDatabaseButton;
    @FXML
    TextField UsernameTextField;
    @FXML
    TextField PassTextField;
    @FXML
    Label logStatus;

    String passwordEntry;
    String usernameEntry;
    PreparedStatement prepareStatement;
    ResultSet loginResult;



    public void handleInitDatabase(ActionEvent actionEvent) {

        System.out.println("Initialize Database!!");
        DatabaseConnection connect = new DatabaseConnection();

        ScriptRunner runner = new ScriptRunner(connect.getConnection(), false, false);
        String file = "C:\\Users\\ianmu\\OneDrive\\Documents\\GitHub\\LoginSystem\\CreateTables.sql";
        try {
            runner.runScript(new BufferedReader(new FileReader(file)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


        //Username query
        String loginQuery = "SELECT username, password, id FROM users where username= ?";


        try {
            //create prepared statements
            prepareStatement = connectUser.prepareStatement(loginQuery);

            //insert parameters
            prepareStatement.setString(1, usernameEntry);

            //Execute Prepared Statement
            loginResult = prepareStatement.executeQuery();

            //get results
            if(!loginResult.isBeforeFirst()){
                logStatus.setText("Try Again");
            }else {
                while (loginResult.next()) {
                    if (usernameEntry.equals(loginResult.getString("username")) && passwordEntry.equals(loginResult.getString("password"))) {
                        logStatus.setText("Login successful!");
                        int id = loginResult.getInt("id");
                        login.setUserID(id);
                        login.setUserName(loginResult.getString("username"));
                        login.changeScene("blog-page.fxml");
                    } else {
                        logStatus.setText("Try Again");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleQueryDatabase(ActionEvent actionEvent) throws IOException {
        login.changeScene("query-page.fxml");
    }
}