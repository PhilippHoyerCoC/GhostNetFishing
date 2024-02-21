package com.iu;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Named
@SessionScoped
@Getter
@Setter
public class Login implements Serializable {

    private static final Logger logger = LogManager.getLogger(Login.class);

    private static final long serialVersionUID = 1094801825228386363L;

    private String userName;
    private String password;
    private String message;

    public String validateUsernamePassword() {
        boolean valid = LoginDAO.validate(userName, password);
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

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        logger.info("Logout successful of user with username: {}", userName);
        return "index";
    }
}
