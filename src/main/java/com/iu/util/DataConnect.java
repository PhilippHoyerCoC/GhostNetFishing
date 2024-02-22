package com.iu.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataConnect {

    private static final Logger logger = LogManager.getLogger(DataConnect.class);

    private DataConnect() {
        throw new AssertionError("DataConnect class should be instantiated.");
    }

    public static Connection getConnection() {
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            logger.error("Database.getConnection() Error --> {}", e.getMessage());
            return null;
        }
    }

    public static void close (Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            logger.error("Error when closing the connection --> {}", e.getMessage());
        }
    }
}
