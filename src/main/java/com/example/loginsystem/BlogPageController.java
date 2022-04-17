package com.example.loginsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

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




    public void handleBlogDoneButton(ActionEvent actionEvent) {
        Blog blog = new Blog(BlogSubjectTextField.getText(), BlogDescriptionTextArea.getText(), loginPage.userID);

    }

    public void handleBlogGoBackButton(ActionEvent actionEvent) throws IOException {
        loginPage.changeScene("login-page.fxml");
    }
}
