package com.iu.session;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
@SessionScoped
@Getter
@Setter
public class Login implements Serializable {

    private static final Logger logger = LogManager.getLogger(Login.class);

    private String userName;
    private String password;
    private String message;

    public String validateUsernamePassword() {
        boolean valid = validate(userName, password);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("userName", userName);
            logger.info("Login successful of user: {}", userName);
            return "user";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Password",
                            "Please enter correct username and Password")
            );
            logger.warn("Login failed");
            return "login";
        }
    }

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

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        logger.info("Logout successful of user with username: {}", userName);
        return "logout";
    }
}
