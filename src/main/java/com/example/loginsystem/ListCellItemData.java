package com.example.loginsystem;

import com.example.loginsystem.Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public String enteredComment;
    public ArrayList<String> comments = new ArrayList<String>();

    ObservableList observableList = FXCollections.observableArrayList();

    public boolean likeordislike;

    public ListCellItemData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loginsystem/list-cell-item.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Blog blog){
        listCellSubject.setText(blog.getSubject());
        listCellUsername.setText("By: " + blog.getUserName());
        listCellDescriptionTextArea.setText(blog.getDescription());

        //TODO: Comment list view


    }


    public void handleListCellSendButton(ActionEvent actionEvent) {
        //enteredComment = listCellCommentTextArea.getText();
        //comments.add(LoginPage.userName + "/" + enteredComment);
        //setCommentListView();
        //create connection
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;

        //Comment Post query
        String commentPostQuery = "INSERT INTO comments(usersID, Description, Sentiment, blogID) VALUES(?,?,?,?);";

        //Prepared Statement
       /* try{
            preparedStatement = connectUser.prepareStatement(commentPostQuery);
            preparedStatement.setString(1, Loginpage);
            preparedStatement.setString(2, BlogDescriptionTextArea.getText());
            String id = String.valueOf(loginPage.getUserID());
            preparedStatement.setString(3, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        setAddedlistView(); */

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

    public void setCommentListView(){
        observableList.setAll(comments);
        listCellCommentListView.setItems(observableList);
        listCellCommentListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView)
            {
                return new ListCommentCell();
            }
        });

    }


}
