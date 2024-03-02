package com.iu.session;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthorizationFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AuthorizationFilter.class);

    public AuthorizationFilter() {
        // No setup is needed for this filter when it is created, hence the constructor is empty.
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // No initialization is needed for this filter    
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            HttpSession ses = servletRequest.getSession(false);

            String reqURI = servletRequest.getRequestURI();
            if (reqURI.indexOf("/login.xhtml") >= 0 || (ses != null && ses.getAttribute("userName") != null)
                    || reqURI.indexOf("/public/") >= 0 || reqURI.contains("javax.faces.resource")) {
                chain.doFilter(request, response);
            } else {
                servletResponse.sendRedirect(servletRequest.getContextPath() + "/faces/login.xhtml");
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void destroy() {
        // No cleanup is needed for this filter
    }

}
