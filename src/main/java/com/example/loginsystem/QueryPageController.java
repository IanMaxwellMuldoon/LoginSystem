package com.example.loginsystem;

import com.example.loginsystem.Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;


public class QueryPageController {

    @FXML
    public TextArea fullQueryText;
    @FXML
    public TextField xTextField;
    @FXML
    public TextField yTextField;
    @FXML
    public Button executeButton;
    @FXML
    public Label answerText;
    @FXML
    public Button goBackButton;
    @FXML
    ComboBox<String> queryComboBox;

    String queryNumber;



    public void handleQueryComboBox(ActionEvent actionEvent)  {
        String s = queryComboBox.getValue();
        fullQueryText.setText(s);
        String[] queryStatement = s.split("\\.");
        queryNumber = queryStatement[0];
    }
    public PreparedStatement queryPreparedStatement(String queryStatement) throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;

        preparedStatement = connectUser.prepareStatement(queryStatement);

        return preparedStatement;
    }
    public String queryOne() throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<String> result = new ArrayList<>();
        Set<String> stringSet = new HashSet<>();

        String X = xTextField.getText();
        String Y = yTextField.getText();
        if (X.equals("") || Y.equals("")) {
            System.out.println("You must enter in values for X and Y");
        } else {

            preparedStatement = queryPreparedStatement("SELECT username FROM users WHERE id IN (SELECT usersID FROM blog WHERE id IN (SELECT blogID FROM tags WHERE Title = ? ));");
            preparedStatement.setString(1, X);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String queryResult = resultSet.getString("username");
                stringSet.add(queryResult);
            }
            preparedStatement = queryPreparedStatement("SELECT username FROM users WHERE id IN (SELECT usersID FROM blog WHERE id IN (SELECT blogID FROM tags WHERE Title = ? ));");
            preparedStatement.setString(1,Y);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String queryResult = resultSet.getString("username");
                if (stringSet.add(queryResult) == false) {
                    result.add(queryResult);
                }

            }
            String listString = String.join(", ", result);
            answerText.setText(listString);
            return listString;
        }
        return "";
    }
    public String queryTwo() throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String result = "";
        String X = xTextField.getText();
        if(X.equals("")){
            System.out.println("Enter the username for X");
        }else {
            String id = "";
            preparedStatement = queryPreparedStatement("SELECT id FROM users WHERE username = ?");
            preparedStatement.setString(1, X);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                id = resultSet.getString("id");
            }

            preparedStatement = queryPreparedStatement("SELECT Subject FROM blog WHERE usersID = "+id+" AND id IN (SELECT blogID FROM comments WHERE Sentiment = '1' AND blogID NOT IN (SELECT blogID FROM comments WHERE Sentiment = '0'));");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("Subject");
            }
            answerText.setText(result);
            return result;
        }
        return "";
    }







    public void handleGoBackButton(ActionEvent actionEvent) throws IOException {
        LoginPage loginPage = new LoginPage();
        loginPage.changeScene("login-page.fxml");

    }


    public void handleExecuteButton(ActionEvent actionEvent) throws SQLException {
        switch(queryNumber){
            case "1": queryOne();
                break;
            case "2": queryTwo();
                break;

        }

    }
}
