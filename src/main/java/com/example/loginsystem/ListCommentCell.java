package com.example.loginsystem;

import javafx.scene.control.ListCell;

public class ListCommentCell extends ListCell<String>
{
    @Override
    public void updateItem(String string, boolean empty)
    {
        super.updateItem(string,empty);
        if(string != null)
        {
           ListCommentData data = new ListCommentData();
           data.setInfo(string);
           setGraphic(data.getBox());
        }
    }
}
