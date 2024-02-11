package com.iu;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public static String getUsername() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return session.getAttribute("userName").toString();
    }

    public static String getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("userId");
        } else {
            return null;
        }
    }
}