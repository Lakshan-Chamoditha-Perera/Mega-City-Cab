package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.configuration.filters.SecurityFilter;
import com.megacitycab.megacitycabservice.entity.custom.User;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.repository.RepositoryType;
import com.megacitycab.megacitycabservice.repository.custom.UserRepository;
import com.megacitycab.megacitycabservice.repository.factory.RepositoryFactory;
import com.megacitycab.megacitycabservice.service.custom.AuthService;
import com.megacitycab.megacitycabservice.util.TransactionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthServiceImpl implements AuthService {

    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    private static final String LOGIN_URL = "/auth/login";
    private static final String REGISTER_URL = "/auth/register";
    private static final String DASHBOARD_URL = "/home";

    private final TransactionManager transactionManager;
    private final UserRepository userRepository;

    public AuthServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.userRepository = RepositoryFactory.getInstance().getRepository(RepositoryType.USER);
    }

    @Override
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.info("Attempting to log in user");

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/auth/login?error=invalid_input");
                return;
            }

            Optional<User> user = transactionManager.doReadOnly(connection -> userRepository.findByEmail(email, connection));

            if (user.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/auth/login?error=user_not_found");
                return;
            }

            User userEntity = user.get();

            if (!userEntity.getPasswordHash().equals(password)) {
                response.sendRedirect(request.getContextPath() + "/auth/login?error=invalid_credentials");
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", userEntity);
            session.setAttribute("userId", userEntity.getUserId());
            session.setAttribute("userEmail", userEntity.getEmail());

            String sessionToken = UUID.randomUUID().toString();
            session.setAttribute("session_token", sessionToken);

            response.sendRedirect(request.getContextPath() + "/home");

        } catch (Exception e) {
            logger.severe("Error during login: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/auth/login?error=system_error");
        }
    }

    @Override
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.info("Registering new user");

            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String username = request.getParameter("username");

            Optional<User> existingUser = transactionManager.doReadOnly(
                    conn -> userRepository.findByEmail(email, conn));

            if (existingUser.isPresent()) {
                response.sendRedirect(request.getContextPath() + REGISTER_URL + "?error=User already exists for " + email);
                return;
            }

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPasswordHash(password);
            transactionManager.doInTransaction(
                    conn -> userRepository.save(newUser, conn));

            response.sendRedirect(request.getContextPath() + REGISTER_URL + "?success=true");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException during registration", e);
            response.sendRedirect(request.getContextPath() + REGISTER_URL + "?error=" + e.getMessage());
        } catch (MegaCityCabException e) {
            throw new RuntimeException("Database error during registration", e);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.info("Logging out user");
            HttpSession session = request.getSession(false);

            if (session != null) {
                SecurityFilter.handleLogout(request);
                session.invalidate();
            }

            response.sendRedirect(request.getContextPath() + LOGIN_URL + "?logout=success");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during logout", e);
            response.sendRedirect(request.getContextPath() + LOGIN_URL + "?error=logout_failed");
        }
    }
}