package com.megacitycab.megacitycabservice.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "appServlet", value = "/app")
public class MainWebServlet extends HttpServlet {

    private String message;

    @Override
    public void init() {
        // Initialize the message
        message = "Instance is up";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/manage_customer.jsp").forward(request, response);
//        try {
//            DatabaseConnectionPool.getConnection();
//        System.out.println("Database connection established");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void destroy() {
    }
}
