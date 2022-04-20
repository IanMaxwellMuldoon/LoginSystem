package com.example.loginsystem;

import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<Blog> {
    @Override
    public void updateItem(Blog blog, boolean empty)
    {
        super.updateItem(blog,empty);
        if(blog != null)
        {
            ListCellItemData data = new ListCellItemData();
            data.setInfo(blog);
            setGraphic(data.getListCellBorderPane());
        }
    }
}
