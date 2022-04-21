package com.example.loginsystem;

import com.example.loginsystem.Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BlogPageController implements Initializable {
    LoginPage loginPage = new LoginPage();
    @FXML
    TextArea BlogDescriptionTextArea;
    @FXML
    Button BlogDoneButton;
    @FXML
    Button BlogGoBackButton;
    @FXML
    TextField BlogSubjectTextField;
    @FXML
    ListView listView;

    ArrayList<Blog> blogList = new ArrayList<Blog>();
    ObservableList observableList = FXCollections.observableArrayList();



// hello
    public void handleBlogDoneButton(ActionEvent actionEvent) {

        if(check2Blogs() == false) {
            //create connection
            DatabaseConnection connection = new DatabaseConnection();
            Connection connectUser = connection.getConnection();
            PreparedStatement preparedStatement;

            //Blog Post query
            String blogPostQuery = "INSERT INTO Blog(Subject, Description, usersID) VALUES(?,?,?)";

            //Prepared Statement
            try {
                preparedStatement = connectUser.prepareStatement(blogPostQuery);
                preparedStatement.setString(1, BlogSubjectTextField.getText());
                preparedStatement.setString(2, BlogDescriptionTextArea.getText());
                String id = String.valueOf(loginPage.getUserID());
                preparedStatement.setString(3, id);


                if (!(BlogDescriptionTextArea.getText().chars().count() < 1000))
                    System.out.println("Error: Maximum of 1000 characters");

                else {
                    preparedStatement.executeUpdate();
                    setAddedlistView();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("You have already posted 2 blogs today!!!");
        }


    }

    public void handleBlogGoBackButton(ActionEvent actionEvent) throws IOException {
        loginPage.changeScene("login-page.fxml");
    }

    public void handleBlogTagTextField(ActionEvent actionEvent) {

    }

    public ArrayList<Blog> getBlogList() {
        return blogList;
    }

    public void setDefaultlistView(){
        //inset Blogs from database
        //create connection
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        //Get blogs query and username
        String getBlogQuery = "SELECT blog.Subject, blog.Description, blog.usersID, users.username, blog.id FROM blog, users WHERE blog.usersID = users.id;";

        try{
            preparedStatement = connectUser.prepareStatement(getBlogQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Blog blog = new Blog(resultSet.getString("Subject"),resultSet.getString("Description"), Integer.parseInt(resultSet.getString("usersID")), resultSet.getString("username"),Integer.parseInt(resultSet.getString("id")));
                blogList.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        observableList.setAll(blogList);
        listView.setItems(observableList);
        listView.setCellFactory(new Callback<ListView<Blog>, ListCell<Blog>>() {
            @Override
            public ListCell<Blog> call(ListView<Blog> listView) {
                return new ListViewCell();
            }
        });

    }
    public boolean check2Blogs() {
        //Check if the user has posted 2 blogs
        //create connection
        boolean check = false;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        //Get number of blogs posted today
        String getCheckBlogQuery = "SELECT count(*) FROM blog WHERE createdOn >= curdate() AND usersID = " + LoginPage.userID + ";";
        try {
            preparedStatement = connectUser.prepareStatement(getCheckBlogQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(Integer.valueOf(resultSet.getString("count(*)")) >= 2){
                    check = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }


    public void setAddedlistView(){
        //inset Blogs from database
        //create connection
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        //Get blogs query and username
        String getBlogQuery = "SELECT blog.Subject, blog.Description, blog.id , blog.usersID, users.username FROM blog, users WHERE blog.usersID = "+LoginPage.userID+" HAVING max(blog.id);";

        try{
            preparedStatement = connectUser.prepareStatement(getBlogQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Blog blog = new Blog(resultSet.getString("Subject"),resultSet.getString("Description"), Integer.parseInt(resultSet.getString("usersID")), LoginPage.userName, Integer.parseInt(resultSet.getString("id")));
                blogList.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        observableList.setAll(blogList);
        listView.setItems(observableList);
        listView.setCellFactory(new Callback<ListView<Blog>, ListCell<Blog>>() {
            @Override
            public ListCell<Blog> call(ListView<Blog> listView) {
                return new ListViewCell();
            }
        });

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefaultlistView();
    }
}

