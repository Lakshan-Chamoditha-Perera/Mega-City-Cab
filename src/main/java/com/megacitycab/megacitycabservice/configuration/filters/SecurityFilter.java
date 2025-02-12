package com.megacitycab.megacitycabservice.configuration.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecurityFilter implements Filter {
    private final Logger logger = Logger.getLogger(SecurityFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("Security Filter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Check if the user is authenticated
        boolean isAuthenticated = (session != null && session.getAttribute("user") != null);
        logger.log(Level.ALL,"isAuthenticated: " + isAuthenticated);

        String servletPath = httpRequest.getServletPath();
//        System.out.println(servletPath);
        // Public endpoints (no authentication required)
        if (servletPath.startsWith("/auth")) {
            chain.doFilter(request, response); // Allow access to public endpoints
            return;
        }

        // Protected endpoints (authentication required)
        if (!isAuthenticated) {
            logger.info("Security Filter - Not Authenticated. Redirecting to Login Page");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login"); // Redirect to login page
            return;
        }

        // Check user role for authorization
//        String userRole = (String) session.getAttribute("role");

        // Example: Only allow "admin" role to access /admin endpoints
//        if (servletPath.contains("/admin") && !"admin".equals(userRole)) {
//            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied"); // 403 Forbidden
//            return;
//        }

        // Allow access to the requested resource
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup logic (if needed)
    }
}