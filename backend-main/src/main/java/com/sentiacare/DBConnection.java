package com.sentiacare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    static Connection conn;
    static Statement statement;

    static String username = "root";
    static String password = "";

    public static Connection getconnection(String db){
        try{
            String database = "jdbc:mysql://localhost:3306/"+db;
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(database, username, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getconnection() {
        try{
            String database = "jdbc:mysql://localhost:3306/";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(database, username, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
