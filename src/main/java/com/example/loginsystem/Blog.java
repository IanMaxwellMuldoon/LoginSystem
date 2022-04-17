package com.example.loginsystem;

public class Blog {

    private String Subject;
    private String Description;
    private int usersID;

    public Blog(){
        Subject = "";
        Description = "";
        usersID = 0;
    }
    public Blog(String Subject,String Description,int usersID){
        this.Subject = Subject;
        this.Description = Description;
        this.usersID = usersID;
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

}
