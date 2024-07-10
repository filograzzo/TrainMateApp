package com.trainmate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/train_mate";
    private static final String USER = "root";
    private static final String PASSWORD = "21Luglio2002";

    static {
        try {
            // Ensure the JDBC driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        // Establish and return a connection to the database
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
