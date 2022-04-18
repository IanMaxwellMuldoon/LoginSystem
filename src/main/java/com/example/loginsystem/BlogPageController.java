package com.example.loginsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

public class BlogPageController {
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




    public void handleBlogDoneButton(ActionEvent actionEvent) {
        System.out.println("Done Button");
        Blog blog = new Blog(BlogSubjectTextField.getText(), BlogDescriptionTextArea.getText(), loginPage.getUserID(), loginPage.getUserName());
        System.out.println("I created the blog");
        blogList.add(blog);
        System.out.println("I added the blog");
        setlistView();
        System.out.println("I set the list view");

    }

    public void handleBlogGoBackButton(ActionEvent actionEvent) throws IOException {
        loginPage.changeScene("login-page.fxml");
    }

    public void handleBlogTagTextField(ActionEvent actionEvent) {

    }

    public ArrayList<Blog> getBlogList() {
        return blogList;
    }

    public void setlistView(){
        observableList.setAll(blogList);
        System.out.println("set observable list");
        listView.setItems(observableList);
        System.out.println("listview.setItems Done");
        listView.setCellFactory(new Callback<ListView<Blog>, ListCell<Blog>>() {
            @Override
            public ListCell<Blog> call(ListView<Blog> listView) {
                System.out.println("I am in set List View");
                return new ListViewCell();
            }
        });

        }
    }

