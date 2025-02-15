package com.megacitycab.megacitycabservice.servlet;

import com.megacitycab.megacitycabservice.entity.custom.User;
import com.megacitycab.megacitycabservice.service.ServiceType;
import com.megacitycab.megacitycabservice.service.custom.UserService;
import com.megacitycab.megacitycabservice.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


@WebServlet(name = "userServlet", value = "/users")
public class UserServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private UserService userService;

    @Override
    public void init() {
        logger.info("UserServlet init");
        userService = ServiceFactory.getInstance().getService(ServiceType.USER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("UserServlet doGet");
            // Fetch the list of users
            List<User> users = userService.findAll();

            // Set the users list as a request attribute
            request.setAttribute("users", users);

            // Forward the request to the JSP page
            request.getRequestDispatcher("/userPage.jsp").forward(request, response);
        } catch (RuntimeException e) {
            e.printStackTrace();
            // return error page

        }
    }

}
