package com.example.loginsystem;

import com.example.loginsystem.Database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

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

    public ToggleButton hikingButton;
    public ToggleButton swimmingButton;
    public ToggleButton dancingButton;
    public ToggleButton calligraphyButton;
    public ToggleButton bowlingButton;
    public ToggleButton movieButton;
    public ToggleButton cookingButton;



    private String strRegUsername;
    private String strRegPassword;
    private String strRePassword;
    private String strFirstName;
    private String strLastName;
    private String strEmail;

    private boolean hiking;
    private boolean swimming;
    private boolean calligraphy;
    private boolean bowling;
    private boolean movie;
    private boolean cooking;
    private boolean dancing;

    private int lastInsertedUserID;


    DatabaseConnection connection = new DatabaseConnection();
    Connection connectUser = connection.getConnection();


    LoginPage login = new LoginPage();


    public void handleRegister(ActionEvent actionEvent) {

        strRegUsername = RegUsername.getText();
        strRegPassword = RegPassword.getText();
        strRePassword = RePassword.getText();
        strFirstName = FirstName.getText();
        strLastName = LastName.getText();
        strEmail = Email.getText();

        connection = new DatabaseConnection();
        connectUser = connection.getConnection();


        //retype password
        if (!strRegPassword.equals(strRePassword)) {
            statusText.setText("Passwords are not the same");
            clearFields();
        }else if(strRegUsername.equals("") || strRegPassword.equals("") || strRePassword.equals("") || strFirstName.equals("") || strLastName.equals("") || strEmail.equals("")){
            statusText.setText("Please fill all fields");
        }
        else {
            String dupQuery = "SELECT count(1) FROM users WHERE username = ? OR email = ?;";
            String regQuery = "INSERT INTO users (username,password,firstname,lastname,email) VALUES (?, ?, ?, ?, ?);";

            try {
                PreparedStatement preparedStatement;

                preparedStatement = connectUser.prepareStatement(dupQuery);

                preparedStatement.setString(1, strRegUsername);
                preparedStatement.setString(2, strEmail);

                ResultSet resultSet;

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
                            //add hobbies
                            if (hikingButton.isSelected())
                                hiking = true;
                            if (swimmingButton.isSelected())
                                swimming = true;
                            if (dancingButton.isSelected())
                                dancing = true;
                            if (calligraphyButton.isSelected())
                                calligraphy = true;
                            if (bowlingButton.isSelected())
                                bowling = true;
                            if (movieButton.isSelected())
                                movie = true;
                            if (cookingButton.isSelected())
                                cooking = true;

                            String hobbiesPostQuery = "INSERT INTO hobbies(usersID, hiking, swimming, calligraphy, bowling, movie, cooking, dancing) VALUES (?, ?, ?, ?, ?,?,?,?);";
                            String getuserID = "SELECT id FROM users ORDER BY id DESC LIMIT 1;";
                            try {
                                preparedStatement = connectUser.prepareStatement(getuserID);
                                resultSet = preparedStatement.executeQuery();
                                while(resultSet.next())
                                    lastInsertedUserID = resultSet.getInt("id");


                                preparedStatement = connectUser.prepareStatement(hobbiesPostQuery);

                                preparedStatement.setString(1, String.valueOf(lastInsertedUserID));
                                preparedStatement.setString(2, boolToString(hiking));
                                preparedStatement.setString(3, boolToString(swimming));
                                preparedStatement.setString(4, boolToString(dancing));
                                preparedStatement.setString(5, boolToString(calligraphy));
                                preparedStatement.setString(6, boolToString(bowling));
                                preparedStatement.setString(7, boolToString(movie));
                                preparedStatement.setString(8, boolToString(cooking));

                                preparedStatement.executeUpdate();


                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

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
    public String boolToString(boolean input) {
        if (input == false) {
            return "0";
        } else {
            return "1";
        }
    }

    public void handleGoBack(ActionEvent actionEvent) throws IOException {
        login.changeScene("login-page.fxml");
    }

    public void clearFields() {
        RegPassword.clear();
        RePassword.clear();

    }

}