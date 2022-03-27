package com.example.loginsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterPageController {

    public TextField RegUsername;
    public TextField RegPassword;
    public TextField RePassword;
    public TextField FirstName;
    public TextField LastName;
    public TextField Email;

    private String strRegUsername;
    private String strRegPassword;
    private String strRePassword;
    private String strFirstName;
    private String strLastName;
    private String strEmail;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    LoginPage login = new LoginPage();





    //@FXML
    /*protected void onHelloButtonClick() {

    }*/

    public void handleRegister(ActionEvent actionEvent) {

        strRegUsername = RegUsername.getText();
        strRegPassword = RegPassword.getText();
        strRePassword = RePassword.getText();
        strFirstName = FirstName.getText();
        strLastName = LastName.getText();
        strEmail = Email.getText();

        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();

        //error detection
        if (!strRegPassword.equals(strRePassword)) {
            System.out.println("Passwords are not the same!!");
            clearFields();
        }
        else {
            String dupQuery = "SELECT count(1) FROM users WHERE username = ?;";
            String regQuery = "INSERT INTO users (username,password,firstname,lastname,email) VALUES (?, ?, ?, ?, ?);";

            try {

                preparedStatement = connectUser.prepareStatement(dupQuery);

                preparedStatement.setString(1, strRegUsername);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    if (resultSet.getInt("count(1)") == 1) {
                        System.out.println("This account already exists!");
                        clearFields();
                    } else {


                        preparedStatement = connectUser.prepareStatement(regQuery);

                        preparedStatement.setString(1, strRegUsername);
                        preparedStatement.setString(2, strRegPassword);
                        preparedStatement.setString(3, strFirstName);
                        preparedStatement.setString(4, strLastName);
                        preparedStatement.setString(5, strEmail);


                        int i = preparedStatement.executeUpdate();

                        if (!resultSet.isBeforeFirst()) {
                            System.out.println("Try Again");
                        } else if (i < 0) {
                            System.out.println("Success!");
                        } else {
                            System.out.println("Failure");
                        }
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();


            }
        }
        }



    public void handleGoBack(ActionEvent actionEvent) throws IOException {
        login.changeScene("login-page.fxml");
    }
    public void clearFields(){
        RegPassword.clear();
        RePassword.clear();
    }
}