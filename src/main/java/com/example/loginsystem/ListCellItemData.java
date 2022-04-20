package com.example.loginsystem;

import com.example.loginsystem.Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListCellItemData {
    @FXML
    public BorderPane listCellBorderPane;
    @FXML
    public Label listCellSubject;
    @FXML
    public Label listCellUsername;
    @FXML
    public TextArea listCellDescriptionTextArea;
    @FXML
    public ListView listCellCommentListView;
    @FXML
    public TextArea listCellCommentTextArea;
    @FXML
    public Button listCellSendButton;
    @FXML
    public Button listCellLikeButton;
    @FXML
    public Button listCellDislikeButton;

    public int blogID;

    public String enteredComment;
    public ArrayList<String> comments = new ArrayList<String>();

    ObservableList observableList = FXCollections.observableArrayList();

    public Boolean likeordislike;

    public ListCellItemData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loginsystem/list-cell-item.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Blog blog) {
        blogID = blog.getBlogID();
        listCellSubject.setText(blog.getSubject());
        listCellUsername.setText("By: " + blog.getUserName());
        listCellDescriptionTextArea.setText(blog.getDescription());
        setDefaultCommentlistView();

    }


    public void handleListCellSendButton(ActionEvent actionEvent) {

        if (likeordislike == null)
            System.out.println("You must select like or dislike");
        else if (check3Comment())
            System.out.println("You have already posted 3 comments today");
        else if (check1CommentEachBlog())
            System.out.println("You may only post 1 comment on each blog");
        else if (checkSelfComment())
            System.out.println("You may not comment on your own blog");
        else {


            //create connection
            DatabaseConnection connection = new DatabaseConnection();
            Connection connectUser = connection.getConnection();
            PreparedStatement preparedStatement;

            //Comment Post query
            String commentPostQuery = "INSERT INTO comments(usersID, Description, Sentiment, blogID) VALUES(?,?,?,?);";

            //Prepared Statement
            try {
                preparedStatement = connectUser.prepareStatement(commentPostQuery);
                preparedStatement.setString(1, String.valueOf(LoginPage.userID));
                preparedStatement.setString(2, listCellCommentTextArea.getText());
                if (likeordislike == true)
                    preparedStatement.setString(3, "1");
                else
                    preparedStatement.setString(3, "0");
                preparedStatement.setString(4, String.valueOf(blogID));


                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            //call the method that loads the latest comment
            setAddedCommentlistView();

        }

    }

    public void setDefaultCommentlistView() {

        //insert Comment from database
        //create connection
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        //Get comment query and username
        String getDefaultCommentQuery = "SELECT comments.Description, comments.usersID, users.username FROM comments, users WHERE comments.blogID = " + blogID + " AND comments.usersID = users.id;";

        try {
            preparedStatement = connectUser.prepareStatement(getDefaultCommentQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String comment = resultSet.getString("username") + "~" + resultSet.getString("Description");
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setCommentListView();

    }

    public void setAddedCommentlistView() {
        //insert Comment from database
        //create connection
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        //Get blogs query and username
        String getCommentQuery = "SELECT comments.Description, comments.usersID, users.username FROM comments, users WHERE comments.usersID = users.id ORDER BY comments.id DESC LIMIT 1;";

        try {
            preparedStatement = connectUser.prepareStatement(getCommentQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String comment = resultSet.getString("username") + "/" + resultSet.getString("Description");
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setCommentListView();

    }

    public boolean check1CommentEachBlog() {
        boolean check = false;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        //Get blogs query and username
        String getCommentQuery = "SELECT count(*) FROM comments WHERE usersID = " + LoginPage.userID + " AND blogID = " + blogID + ";";

        try {
            preparedStatement = connectUser.prepareStatement(getCommentQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (Integer.valueOf(resultSet.getString("count(*)")) >= 1) {
                    check = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean checkSelfComment() {
        boolean check = false;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        //Get blogs query and username
        String getCommentQuery = "SELECT usersID FROM blog WHERE id = " + blogID + ";";

        try {
            preparedStatement = connectUser.prepareStatement(getCommentQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (Integer.valueOf(resultSet.getString("usersID")) == LoginPage.userID) {
                    check = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean check3Comment() {
        boolean check = false;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        //Get blogs query and username
        String getCommentQuery = "SELECT count(*) FROM comments WHERE createdOn >= curdate() AND usersID = " + LoginPage.userID + ";";

        try {
            preparedStatement = connectUser.prepareStatement(getCommentQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (Integer.valueOf(resultSet.getString("count(*)")) >= 3) {
                    check = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }


    public void handleListCellLikeButton(ActionEvent actionEvent) {
        likeordislike = true;
    }

    public void handleListCellDislikeButton(ActionEvent actionEvent) {
        likeordislike = false;
    }

    public BorderPane getListCellBorderPane() {
        return listCellBorderPane;
    }

    public void setCommentListView() {
        observableList.setAll(comments);
        listCellCommentListView.setItems(observableList);
        listCellCommentListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCommentCell();
            }
        });

    }

}
