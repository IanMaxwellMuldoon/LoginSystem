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
    public Label statusText;

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
            statusText.setText("Passwords are not the same!!");
            clearFields();
        }
        else {
            String dupQuery = "SELECT count(1) FROM users WHERE username = ? OR email = ?;";
            String regQuery = "INSERT INTO users (username,password,firstname,lastname,email) VALUES (?, ?, ?, ?, ?);";

            try {

                preparedStatement = connectUser.prepareStatement(dupQuery);

                preparedStatement.setString(1, strRegUsername);
                preparedStatement.setString(2, strEmail);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    if (resultSet.getInt("count(1)") > 1) {
                        statusText.setText("This account already exists!");
                        clearFields();
                    } else {


                        preparedStatement = connectUser.prepareStatement(regQuery);

                        preparedStatement.setString(1, strRegUsername);
                        preparedStatement.setString(2, strRegPassword);
                        preparedStatement.setString(3, strFirstName);
                        preparedStatement.setString(4, strLastName);
                        preparedStatement.setString(5, strEmail);


                        int i = preparedStatement.executeUpdate();
                        if (i > 0) {
                            statusText.setText("Success!");
                        } else {
                            statusText.setText("Failure");
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