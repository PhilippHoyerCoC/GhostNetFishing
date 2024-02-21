package com.iu;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataConnect {

    private static final Logger logger = LogManager.getLogger(DataConnect.class);

    public static Connection getConnection() {
        //TODO: Use environment variables for the database connection
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/ghostnet", "ghostnet", "geheim");
            return con;
        } catch (Exception e) {
            logger.error("Database.getConnection() Error --> {}", e.getMessage());
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
