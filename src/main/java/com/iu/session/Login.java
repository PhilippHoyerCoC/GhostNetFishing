package com.iu.session;

import com.iu.dao.UserDAO;
import com.iu.model.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

    @Inject
    private UserDAO userDAO;

    public String validateUsernamePassword() {
        boolean valid = validate(userName, password);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("userName", userName);
            logger.info("Login successful of user: {}", userName);
            return "/ghostnet?faces-redirect=true";
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

    @Transactional
    public boolean validate(String user, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        logger.info("Validate user: {}", user);

        User actualUser = userDAO.getUserByUsername(user);
        return actualUser != null && actualUser.getPassword().equals(password);
    }

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        logger.info("Logout successful of user: {}", userName);
        return "index?faces-redirect=true";
    }
}
