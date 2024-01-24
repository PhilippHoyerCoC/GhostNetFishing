package com.iu;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnect {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/ghostnet", "ghostnet", "geheim");
            return con;
        } catch (Exception e) {
            System.out.println("Database.getConnection() Error -->" + e.getMessage());
            return null;
        }
    }

    public static void close (Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
        }
    }
}
