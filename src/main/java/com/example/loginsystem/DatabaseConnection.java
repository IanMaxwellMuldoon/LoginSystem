package com.example.loginsystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "userdatabase";
        String databaseUser = "comp440";
        String databasePassword = "pass1234";

        String url = "jdbc:mysql://localhost/" + databaseName;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
