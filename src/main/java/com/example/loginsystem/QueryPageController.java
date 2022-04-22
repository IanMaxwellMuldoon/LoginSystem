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
import java.sql.SQLException;
import java.util.ResourceBundle;


public class QueryPageController implements Initializable {

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



    public void handleQueryComboBox(ActionEvent actionEvent) {
        fullQueryText.setText("" + queryComboBox.getValue());
    }
    public String Query(String queryStatement) throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectUser = connection.getConnection();
        PreparedStatement preparedStatement;

        preparedStatement = connectUser.prepareStatement(queryStatement);



    }


    public void handleGoBackButton(ActionEvent actionEvent) throws IOException {
        LoginPage loginPage = new LoginPage();
        loginPage.changeScene("login-page.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
