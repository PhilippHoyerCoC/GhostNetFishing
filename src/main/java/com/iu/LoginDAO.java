package com.iu;

import java.sql.*;

public class LoginDAO {
    public static boolean validate(String user, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            System.out.println("user: " + user + " password: " + password);
            connection = DataConnect.getConnection();
            preparedStatement = connection.prepareStatement("Select username, password from user where username = ? and password = ?");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());
        } finally {
            DataConnect.close(connection);
        }
        return false;
    }
}
