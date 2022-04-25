package com.example.loginsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ListviewController {
    @FXML
    private ListView listView;
    private ArrayList<Blog> blogList;
    ObservableList observableList = FXCollections.observableArrayList();

    public ListviewController(){
        
    }

}
