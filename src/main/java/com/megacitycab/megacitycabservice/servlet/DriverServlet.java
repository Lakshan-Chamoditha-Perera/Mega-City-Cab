package com.megacitycab.megacitycabservice.servlet;

import com.megacitycab.megacitycabservice.dto.DriverDTO;
import com.megacitycab.megacitycabservice.service.ServiceType;
import com.megacitycab.megacitycabservice.service.custom.DriverService;
import com.megacitycab.megacitycabservice.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "driverServlet", value = "/drivers")
public class DriverServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DriverServlet.class.getName());

    private DriverService driverService;

    @Override
    public void init() {
        driverService = ServiceFactory.getInstance().getService(ServiceType.DRIVER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("Handling GET request for drivers");

        try {
            List<DriverDTO> drivers = driverService.getAllDrivers();
            logger.info("Retrieved drivers: " + drivers.size());
            request.setAttribute("drivers", drivers);
            request.getRequestDispatcher("/manage_driver.jsp").forward(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving drivers", e);
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Error fetching drivers");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("SAVE".equals(action)) {
            handleDriverSave(request, response);
        } else if ("DELETE".equals(action)) {
            handleDriverDelete(request, response);
        } else if ("UPDATE".equals(action)) {
            handleDriverUpdate(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/drivers?error=Invalid action");
        }
    }

    private void handleDriverUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String driverId = request.getParameter("driverId");
        logger.info("Handling UPDATE request for driver ID: " + driverId);

        try {
            boolean success = driverService.updateDriver(
                    DriverDTO.builder()
                            .driverId(Integer.parseInt(driverId))
                            .firstName(request.getParameter("firstName"))
                            .lastName(request.getParameter("lastName"))
                            .licenseNumber(request.getParameter("licenseNumber"))
                            .mobileNo(request.getParameter("mobileNo"))
                            .email(request.getParameter("email"))
                            .build());

            System.out.println(success);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/drivers?success=Driver successfully update");
            } else {
                response.sendRedirect(request.getContextPath() + "/drivers?error=Failed to update driver");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating driver", e);
            response.sendRedirect(request.getContextPath() + "/drivers?error=" + e.getMessage());
        }
    }

    private void handleDriverDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String driverId = request.getParameter("driverId");
        logger.info("Handling DELETE request for driver ID: " + driverId);

        try {
            boolean success = driverService.deleteDriver(Integer.parseInt(driverId));
            if (success) {
                response.sendRedirect(request.getContextPath() + "/drivers?success=Driver successfully deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/drivers?error=Failed to delete driver");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting driver", e);
            response.sendRedirect(request.getContextPath() + "/drivers?error=" + e.getMessage());
        }
    }

    private void handleDriverSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Handling POST request to save driver");
        try {
            boolean success = driverService.saveDriver(
                    DriverDTO.builder()
                            .firstName(request.getParameter("firstName"))
                            .lastName(request.getParameter("lastName"))
                            .licenseNumber(request.getParameter("licenseNumber"))
                            .mobileNo(request.getParameter("mobileNo"))
                            .email(request.getParameter("email"))
                            .build()
                    );


            if (success) {
                response.sendRedirect(request.getContextPath() + "/drivers?success=Driver successfully saved");
            } else {
                response.sendRedirect(request.getContextPath() + "/drivers?error=Failed to save driver");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving driver", e);
            response.sendRedirect(request.getContextPath() + "/drivers?error=" + e.getMessage());
        }
    }
}