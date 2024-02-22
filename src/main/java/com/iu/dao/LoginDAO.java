package com.iu.dao;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.iu.util.DataConnect;


public class LoginDAO {

    private LoginDAO() {
        throw new AssertionError("LoginDAO is a utility class and should not be instantiated");
    }

    private static final Logger logger = LogManager.getLogger(LoginDAO.class);

    public static boolean validate(String user, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        logger.info("Validate user: {}", user);

        try {
            connection = DataConnect.getConnection();
            preparedStatement = connection.prepareStatement("Select username, password from user where username = ? and password = ?");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Login error --> {}", e.getMessage());
        } finally {
            DataConnect.close(connection);
        }
        return false;
    }
}
