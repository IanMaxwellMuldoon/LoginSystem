package com.example.loginsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BlogPage extends Application {

    public static Stage window;


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("blog-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Blog");
        stage.setScene(scene);
        stage.show();
    }

}
