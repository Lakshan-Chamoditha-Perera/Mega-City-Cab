package com.megacitycab.megacitycabservice.configuration.filters;

import com.megacitycab.megacitycabservice.entity.custom.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityFilter implements Filter {

    private static final int SESSION_TIMEOUT = 3600;
    private static final String SESSION_TOKEN = "session_token";
    private static final String TAB_ID = "tab_id";
    private static final String ACTIVE_TAB_ID = "active_tab_id";
    private static final ConcurrentHashMap<String, String> ACTIVE_USER_TABS = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Security Filter triggered");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Allow access to authentication endpoints (public routes)
        if (httpRequest.getServletPath().startsWith("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        if (session != null) {
            System.out.println("Session is already logged in");
            long lastAccessedTime = session.getLastAccessedTime();
            long currentTime = System.currentTimeMillis();
            long inactiveTime = (currentTime - lastAccessedTime) / 1000; // Convert to seconds
            System.out.println("Last accessed time is: " + lastAccessedTime);
            System.out.println("currentTime          : " + currentTime);
            System.out.println("inactiveTime         : " + inactiveTime);

            if (inactiveTime > SESSION_TIMEOUT) {
                System.out.println("Session expired due to inactivity. Logging out user.");
                cleanupSession(session);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login?error=session_expired");
                return;
            }

            session.setMaxInactiveInterval(SESSION_TIMEOUT);

            String storedToken = (String) session.getAttribute(SESSION_TOKEN);
            if (storedToken == null) {
                System.out.println("Session token missing. Logging out user.");
                cleanupSession(session);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login?error=session_invalid");
                return;
            }

            String userId = getUserIdFromSession(session);
            String currentTabId = (String) session.getAttribute(TAB_ID);

            // If this is a new tab (no tab ID yet)
            if (currentTabId == null) {
                // Generate a new tab ID
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

            // If this is not an active tab and the request is not for reading only
            if (!isActiveTab && isWriteOperation(httpRequest)) {
                System.out.println("Attempt to perform action from non-active tab: " + currentTabId);
                httpResponse.setContentType("application/json");
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.getWriter().write("{\"error\":\"Another session is already active. Please use your original tab or logout and login again.\"}");
                return;
            }
        }

        boolean isAuthenticated = (session != null && session.getAttribute("user") != null);
        System.out.println("isAuthenticated: " + isAuthenticated);

        if (!isAuthenticated) {
            System.out.println("Security Filter - Not Authenticated. Redirecting to Login Page");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * Determine if request is a write operation
     * You may need to customize this based on your application's needs
     */
    private boolean isWriteOperation(HttpServletRequest request) {
        String method = request.getMethod();
        return "POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method);
    }

    /**
     * Get user ID from session
     */
    private String getUserIdFromSession(HttpSession session) {
        Object user = session.getAttribute("user");
        // Replace this with your actual user ID extraction logic
        // This is a placeholder implementation
        System.out.println("=================================");
        System.out.println((User)user);
        System.out.println("=================================");
        return user != null ? user.toString() : "anonymous";
    }

    /**
     * Clean up session data
     */
    private void cleanupSession(HttpSession session) {
        String userId = getUserIdFromSession(session);
        String tabId = (String) session.getAttribute(TAB_ID);

        // Remove this tab from active tabs if it was the active one
        if (tabId != null && tabId.equals(ACTIVE_USER_TABS.get(userId))) {
            ACTIVE_USER_TABS.remove(userId);
        }

        session.invalidate();
    }

    /**
     * Handle user logout properly
     * Call this method from your logout endpoint
     */
    public static void handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String userId = session.getAttribute("user") != null ?
                    session.getAttribute("user").toString() : null;
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