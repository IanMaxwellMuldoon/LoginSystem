package com.example.loginsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class ListCommentData
{
    @FXML
    private HBox listCommentHBox;
    @FXML
    private Label listCommentUsername;
    @FXML
    private TextArea listCommentTextArea;

    public ListCommentData()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loginsystem/list-comment-cell.fxml"));
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

    public void setInfo(String string)
    {
        String[] comment = string.split("~");
        listCommentUsername.setText(comment[0]);
        listCommentTextArea.setText(comment[1]);
    }

    public HBox getBox()
    {
        return listCommentHBox;
    }
}
