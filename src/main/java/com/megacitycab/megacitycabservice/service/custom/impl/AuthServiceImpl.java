package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.User;
import com.megacitycab.megacitycabservice.repository.custom.UserRepository;
import com.megacitycab.megacitycabservice.repository.custom.impl.UserRepositoryImpl;
import com.megacitycab.megacitycabservice.service.custom.AuthService;
import com.megacitycab.megacitycabservice.util.TransactionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

public class AuthServiceImpl implements AuthService {

    private final TransactionManager transactionManager;
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    public AuthServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        userRepository = new UserRepositoryImpl();
    }


    @Override
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.info("Attempting to log in user");

            // Get form parameters from the login form
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Validate input
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/auth/login?error=invalid_input");
                return;
            }

            // Fetch user from the database
            Optional<User> user = transactionManager.doReadOnly(connection -> userRepository.findByEmail(email, connection));

            if (user.isEmpty()) {
                // User not found
                response.sendRedirect(request.getContextPath() + "/auth/login?error=user_not_found");
                return;
            }

            User userEntity = user.get();

            // Validate password (in a real-world scenario, compare hashed passwords)
            if (!userEntity.getPasswordHash().equals(password)) {
                // Invalid password
                response.sendRedirect(request.getContextPath() + "/auth/login?error=invalid_credentials");
                return;
            }

            // Create session and store user details
            HttpSession session = request.getSession();
            session.setAttribute("user", userEntity); // Set the entire user object in the session
            session.setAttribute("userId", userEntity.getUserId()); // Optionally store userId
            session.setAttribute("userEmail", userEntity.getEmail()); // Optionally store email


            // Redirect to the dashboard after successful login
            response.sendRedirect(request.getContextPath() + "/customers");

        } catch (Exception e) {
            logger.severe("Error during login: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/auth/login?error=system_error");
        }
    }

    @Override
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.info("Registering new user");
            System.out.println("Context path : " + request.getContextPath());
            // Get form parameters from the registration form
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String username = request.getParameter("username");

            Optional<User> existingUser = transactionManager.doReadOnly(connection -> userRepository.findByEmail(email, connection));

            if (existingUser.isPresent()) {
                response.sendRedirect(request.getContextPath() + "/auth/register?error=" + "User already exists for " + email);
                return;
            }

            // Create a new User object
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPasswordHash(password);  // In a real-world scenario, this should be hashed

            // Save the user in the database
            transactionManager.doInTransaction(connection -> {
                return userRepository.save(newUser, connection);
            });

            // Redirect to the login page after successful registration
            response.sendRedirect(request.getContextPath() + "/auth/register?success=true");

        } catch (IOException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/auth/register?error=" + e.getMessage());
        }
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {

    }
}
