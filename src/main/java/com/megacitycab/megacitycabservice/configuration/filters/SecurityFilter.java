package com.megacitycab.megacitycabservice.configuration.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityFilter implements Filter {

    private static final int SESSION_TIMEOUT = 3600; // Session timeout in seconds
    private static final String SESSION_TOKEN = "session_token";
    private static final String TAB_ID = "tab_id";
    private static final String ACTIVE_TAB_ID = "active_tab_id";
    private static final ConcurrentHashMap<Integer, String> ACTIVE_USER_TABS = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Security Filter triggered");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Allow access to authentication endpoints (public routes)
        if (httpRequest.getServletPath().startsWith("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        // Handle session timeout and validation
        Integer userId = null;
        if (session != null) {
            System.out.println("Session is already logged in");

            // Check session inactivity
            long lastAccessedTime = session.getLastAccessedTime();
            long currentTime = System.currentTimeMillis();
            long inactiveTime = (currentTime - lastAccessedTime) / 1000; // Convert to seconds

//            System.out.println("Last accessed time: " + lastAccessedTime);
//            System.out.println("Current time: " + currentTime);
//            System.out.println("Inactive time: " + inactiveTime);

            if (inactiveTime > SESSION_TIMEOUT) {
                System.out.println("Session expired due to inactivity. Logging out user.");
                cleanupSession(session);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login?error=session_expired");
                return;
            }

            // Reset session timeout
            session.setMaxInactiveInterval(SESSION_TIMEOUT);

            // Validate session token
            String storedToken = (String) session.getAttribute(SESSION_TOKEN);
            if (storedToken == null || !validateSessionToken(session, storedToken)) {
                System.out.println("Invalid or missing session token. Logging out user.");
                cleanupSession(session);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login?error=session_invalid");
                return;
            }

            // Handle tab-based activity tracking
            userId = getUserIdFromSession(session);
            request.setAttribute("userId", userId);
            String currentTabId = (String) session.getAttribute(TAB_ID);

            // If this is a new tab (no tab ID yet)
            if (currentTabId == null) {
                currentTabId = UUID.randomUUID().toString();
                session.setAttribute(TAB_ID, currentTabId);

                // If this is the first tab for this user, mark it as active
                if (!ACTIVE_USER_TABS.containsKey(userId)) {
                    ACTIVE_USER_TABS.put(userId, currentTabId);
                    session.setAttribute(ACTIVE_TAB_ID, currentTabId);
                } else {
                    // Otherwise, get the active tab ID for this user
                    session.setAttribute(ACTIVE_TAB_ID, ACTIVE_USER_TABS.get(userId));
                }
            }

            // Check if this tab is the active one
            boolean isActiveTab = currentTabId.equals(ACTIVE_USER_TABS.get(userId));

            // If this is not an active tab and the request is a write operation
            if (!isActiveTab && isWriteOperation(httpRequest)) {
                System.out.println("Attempt to perform action from non-active tab: " + currentTabId);
                httpResponse.setContentType("application/json");
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.getWriter().write("{\"error\":\"Another session is already active. Please use your original tab or logout and login again.\"}");
                return;
            }
        }

        // Check if the user is authenticated
        boolean isAuthenticated = (session != null && session.getAttribute("userId") != null);
        System.out.println("isAuthenticated: " + isAuthenticated);

        if (!isAuthenticated) {
            System.out.println("Security Filter - Not Authenticated. Redirecting to Login Page");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
            return;
        }

        // Proceed with the request
        chain.doFilter(request, response);
    }

    /**
     * Determine if the request is a write operation (POST, PUT, DELETE, PATCH).
     */
    private boolean isWriteOperation(HttpServletRequest request) {
        String method = request.getMethod();
        return "POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method);
    }

    /**
     * Get the user ID from the session.
     */
    private Integer getUserIdFromSession(HttpSession session) {
        System.out.println("getUserIdFromSession------------------------");
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("userId: " + userId);
        return userId != null ? userId : null; // Return null if userId is not set
    }

    /**
     * Clean up session data and remove the user from active tabs.
     */
    private void cleanupSession(HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        String tabId = (String) session.getAttribute(TAB_ID);

        // Remove this tab from active tabs if it was the active one
        if (userId != null && tabId != null && tabId.equals(ACTIVE_USER_TABS.get(userId))) {
            ACTIVE_USER_TABS.remove(userId);
        }

        // Invalidate the session
        session.invalidate();
    }

    /**
     * Generate a new session token.
     */
    private String generateSessionToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Validate the session token.
     */
    private boolean validateSessionToken(HttpSession session, String token) {
        String storedToken = (String) session.getAttribute(SESSION_TOKEN);
        return storedToken != null && storedToken.equals(token);
    }

    /**
     * Handle user logout properly.
     * Call this method from your logout endpoint.
     */
    public static void handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                ACTIVE_USER_TABS.remove(userId);
            }
            session.invalidate();
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic (if needed)
        ACTIVE_USER_TABS.clear();
    }
}