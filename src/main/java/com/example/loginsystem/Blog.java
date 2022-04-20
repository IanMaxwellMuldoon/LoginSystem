package com.example.loginsystem;

import java.util.ArrayList;

public class Blog {

    private String Subject;
    private String Description;
    private int usersID;
    private String userName;
    private ArrayList<String> comments;
    private ArrayList<String> tags;

    public Blog(){
        Subject = "";
        Description = "";
        usersID = 0;
        userName = "";
    }
    public Blog(String Subject,String Description,int usersID, String userName){
        this.Subject = Subject;
        this.Description = Description;
        this.usersID = usersID;
        this.userName = userName;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getUsersID() {
        return usersID;
    }

    public void setUsersID(int usersID) {
        this.usersID = usersID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
