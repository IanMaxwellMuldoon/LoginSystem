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
import java.util.Arrays;
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
    @FXML
    TextField BlogTagTextField;

    ArrayList<Blog> blogList = new ArrayList<Blog>();
    ObservableList observableList = FXCollections.observableArrayList();




    public void handleBlogDoneButton(ActionEvent actionEvent) {
        System.out.println("Done Button");
        System.out.println(Arrays.toString(splitTags(BlogTagTextField)));

        //create connection
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        PreparedStatement tagpreparedStatement;

        //Blog Post query
        String blogPostQuery = "INSERT INTO Blog(Subject, Description, usersID) VALUES(?,?,?)";

        String tagPostQuery = "INSERT INTO Tags(blogID, Title) VALUES(?, ?)";

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


            tagpreparedStatement = connectUser.prepareStatement(tagPostQuery);
            for(String s: splitTags(BlogTagTextField)) {
                tagpreparedStatement.setString(1, String.valueOf(queryBlogId()));
                tagpreparedStatement.setString( 2,s);
                tagpreparedStatement.executeUpdate();

            }


        } catch (Exception e) {
            e.printStackTrace();
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
        String getBlogQuery = "SELECT blog.Subject, blog.Description, blog.usersID, users.username FROM blog, users WHERE blog.usersID = users.id;";

        try{
            preparedStatement = connectUser.prepareStatement(getBlogQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Blog blog = new Blog(resultSet.getString("Subject"),resultSet.getString("Description"), Integer.parseInt(resultSet.getString("usersID")), resultSet.getString("username"));
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
    public void setAddedlistView(){
        //inset Blogs from database
        //create connection
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        //Get blogs query and username
        String getBlogQuery = "SELECT blog.Subject, blog.Description, blog.usersID, users.username FROM blog, users WHERE blog.usersID = "+LoginPage.userID+" HAVING max(blog.id);";

        try{
            preparedStatement = connectUser.prepareStatement(getBlogQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Blog blog = new Blog(resultSet.getString("Subject"),resultSet.getString("Description"), Integer.parseInt(resultSet.getString("usersID")), resultSet.getString("username"));
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

    public String [] splitTags (TextField BlogTagTextField ) {
        String s = BlogTagTextField.getText();
        String [] sArray = s.split(", ");

        return sArray;

    }

    public int queryBlogId() {
        //inset Blogs from database
        //create connection
        int i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        //Get blogs query and username
        String getBlogQuery = "SELECT count(*) FROM blog";

        try{
            preparedStatement = connectUser.prepareStatement(getBlogQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
               i = Integer.valueOf(resultSet.getString("count(*)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;

    }


}

