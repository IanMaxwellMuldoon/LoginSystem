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
import java.util.*;


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
    //This
    public String queryOne() throws SQLException {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        ArrayList<String> input = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        HashMap<Integer, String> stringMap = new HashMap<Integer, String>();

        String X = xTextField.getText();
        String Y = yTextField.getText();
        if (X.equals("") || Y.equals("")) {
            System.out.println("You must enter in values for X and Y");
        } else {

            preparedStatement = queryPreparedStatement("SELECT users.username, blog.id FROM users, blog WHERE blog.usersID = users.id AND blog.id IN (SELECT blogID FROM tags WHERE Title = ?)");
            preparedStatement.setString(1, X);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String queryResultUsername = resultSet.getString("username");
                int queryResultID = Integer.parseInt((resultSet.getString("id")));
                stringMap.put(queryResultID, queryResultUsername);
            }
            preparedStatement = queryPreparedStatement("SELECT users.username, blog.id FROM users, blog WHERE blog.usersID = users.id AND blog.id IN (SELECT blogID FROM tags WHERE Title = ?);");
            preparedStatement.setString(1, Y);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String queryResultUsername = resultSet.getString("username");
                int queryResultID = Integer.parseInt((resultSet.getString("id")));

                for (Map.Entry<Integer, String> entry : stringMap.entrySet()) {
                    if (entry.getValue().equals(queryResultUsername) && entry.getKey() != queryResultID) {
                        result.add(queryResultUsername);
                    }
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

    public String queryThree() throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<String> result = new ArrayList<>();

        preparedStatement = queryPreparedStatement("WITH CTE_BlogCount AS ( SELECT usersID, count(usersID) AS blogPosts FROM blog WHERE createdON BETWEEN '2022-04-22 00:00:00' AND '2022-04-22 23:59:59' group by usersID order by blogPosts DESC ) , CTE_MaxBlogs AS  ( SELECT MAX(BlogPosts) AS MAX FROM CTE_BlogCount ) SELECT U.Username, Bc.BlogPosts FROM CTE_BlogCount BC , CTE_MaxBlogs MB , Users U WHERE BC.BlogPosts = MB.MAX AND U.ID = BC.Usersid;");
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            String user = resultSet.getString("username");
            result.add(user);
        }
        String listString = String.join(", ", result);
        answerText.setText(listString);
        return listString;
        }

    public String queryFour() throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String X = xTextField.getText();
        String Y = yTextField.getText();
        ArrayList<String> result = new ArrayList<>();

        if (X.equals("") || Y.equals("")) {
            System.out.println("Please enter a username for X and Y");
        } else {
            preparedStatement = queryPreparedStatement("Select username From users Where id IN (select FollowedUserid From follow Where FollowingUserid = (Select id FROM users WHERE username = ?) AND id IN (select FollowedUserid From follow Where FollowingUserid = (Select id FROM users WHERE username = ?)));");
            preparedStatement.setString(1, X);
            preparedStatement.setString(2, Y);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("username"));
            }
            String listString = String.join(", ", result);
            answerText.setText(listString);
            return listString;
        }
        return "";
    }
    public String queryFive() throws SQLException{
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<String> result = new ArrayList<>();

        preparedStatement = queryPreparedStatement("SELECT t1.usersID, t2.usersID FROM hobbies AS t1 JOIN hobbies AS t2 ON t1.usersID < t2.usersID WHERE 2 = ALL (SELECT count(*) FROM hobbies WHERE (t1.hiking = 1 OR t2.hiking = 1) AND (t1.swimming = 1 OR t2.swimming = 1) AND (t1.calligraphy = 1 OR t2.calligraphy = 1) AND (t1.bowling OR t2.bowling = 1) AND (t1.movie = 1 OR t2.movie = 1) AND (t1.cooking = 1 OR t2.cooking = 1) AND (t1.dancing = 1 OR t2.dancing = 1) group by id);");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String userIDOne = resultSet.getString(1);
            String userIDTwo = resultSet.getString(2);
            result.add("(" + userIDOne + "," + userIDTwo + "), ");
        }
        String listString = String.join(" ", result);
        answerText.setText(listString);
        return listString;
    }

    public String querySix() throws SQLException{
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<String> result = new ArrayList<>();

        preparedStatement = queryPreparedStatement("Select id from users Where id Not IN (select usersID From blog);");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String user = resultSet.getString(1);
            result.add(user + ", ");
        }
        String listString = String.join(" ", result);
        answerText.setText(listString);
        return listString;
    }

    public String querySeven() throws SQLException{
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<String> result = new ArrayList<>();

        preparedStatement = queryPreparedStatement("Select username From users Where id Not IN (select usersID From comments);");
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String user = resultSet.getString(1);
            result.add(user + ", ");
        }
        String listString = String.join(" ", result);
        answerText.setText(listString);
        return listString;
    }

    public String queryEight() throws SQLException{
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<String> result = new ArrayList<>();

        preparedStatement = queryPreparedStatement("SELECT username FROM users WHERE id IN (SELECT usersID FROM comments WHERE sentiment = 0 AND usersID NOT IN (SELECT usersID FROM comments WHERE Sentiment = 1));");
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            String user = resultSet.getString(1);
            result.add(user + ", ");
        }
        String listString = String.join(" ", result);
        answerText.setText(listString);
        return listString;
    }

    public String queryNine() throws SQLException{
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<String> result = new ArrayList<>();

        preparedStatement = queryPreparedStatement("SELECT username FROM users WHERE id  IN (SELECT usersID FROM blog WHERE id IN (SELECT blogID FROM comments WHERE Sentiment != 0 ));");
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            String user = resultSet.getString(1);
            result.add(user + ", ");
        }
        String listString = String.join(" ", result);
        answerText.setText(listString);
        return listString;
    }





    public void handleGoBackButton(ActionEvent actionEvent) throws IOException {
        LoginPage loginPage = new LoginPage();
        loginPage.changeScene("login-page.fxml");

    }


    public void handleExecuteButton(ActionEvent actionEvent) throws SQLException {
        if (queryNumber != null) {
            switch (queryNumber) {
                case "1":
                    queryOne();
                    break;
                case "2":
                    queryTwo();
                    break;
                case "3":
                    queryThree();
                    break;
                case "4":
                    queryFour();
                    break;
                case "5":
                    queryFive();
                    break;
                case "6":
                    querySix();
                    break;
                case "7":
                    querySeven();
                    break;
                case "8":
                    queryEight();
                    break;
                case "9":
                    queryNine();
                    break;
            }
        }
    }
}
