package com.megacitycab.megacitycabservice.servlet;

import com.megacitycab.megacitycabservice.dto.custom.CustomerDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.ServiceType;
import com.megacitycab.megacitycabservice.service.custom.CustomerService;
import com.megacitycab.megacitycabservice.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "customerServlet", value = "/customers")
public class CustomerServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CustomerServlet.class.getName());

    private CustomerService customerService;

    @Override
    public void init() {
        customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("Handling GET request for customers");

        try {
            List<CustomerDTO> customers = customerService.getAllCustomers();
            logger.info("Retrieved customers: " + customers.size());
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/manage_customer.jsp").forward(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Internal error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("SAVE".equals(action)) {
            handleCustomerSave(request, response);
        } else if ("DELETE".equals(action)) {
            handleCustomerDelete(request, response);
        } else if ("UPDATE".equals(action)) {
            handleCustomerUpdate(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/customers?error=Invalid action");
        }
    }

    private void handleCustomerUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String customerId = request.getParameter("customerId");
        logger.info("Handling UPDATE request for customer ID: " + customerId);

        try {
            boolean success = customerService.updateCustomer(
                    CustomerDTO.builder()
                            .customerId(Integer.parseInt(customerId))
                            .firstName(request.getParameter("firstName"))
                            .lastName(request.getParameter("lastName"))
                            .address(request.getParameter("address"))
                            .dateOfBirth(Date.valueOf(request.getParameter("dateOfBirth")))
                            .mobileNo(request.getParameter("mobileNo"))
                            .email(request.getParameter("email"))
                            .nic(request.getParameter("nic"))
                            .addedUserId((Integer) request.getAttribute("userId"))
                            .build());

            System.out.println(success);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/customers?success=Customer successfully updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/customers?error=Failed to update customer");
            }
        } catch (MegaCityCabException e) {
            logger.log(Level.SEVERE, "Error updating customer", e);
            response.sendRedirect(request.getContextPath() + "/customers?error=" + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Internal error");
        }
    }

    private void handleCustomerDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String customerId = request.getParameter("customerId");
        logger.info("Handling DELETE request for customer ID: " + customerId);

        try {
            boolean success = customerService.deleteCustomer(Integer.parseInt(customerId));
            if (success) {
                response.sendRedirect(request.getContextPath() + "/customers?success=Customer successfully deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/customers?error=Failed to delete customer");
            }
        } catch (MegaCityCabException e) {
            logger.log(Level.SEVERE, "Error deleting customer", e);
            response.sendRedirect(request.getContextPath() + "/customers?error=" + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Internal error");
        }
    }

    private void handleCustomerSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Handling POST request to save customer");
        try {
            boolean success = customerService.saveCustomer(
                    CustomerDTO.builder()
                            .firstName(request.getParameter("firstName"))
                            .lastName(request.getParameter("lastName"))
                            .address(request.getParameter("address"))
                            .dateOfBirth(Date.valueOf(request.getParameter("dateOfBirth")))
                            .mobileNo(request.getParameter("mobileNo"))
                            .email(request.getParameter("email"))
                            .nic(request.getParameter("nic"))
                            .addedUserId((Integer) request.getAttribute("userId"))
                            .build());
            if (success) {
                response.sendRedirect(request.getContextPath() + "/customers?success=Customer successfully saved");
            } else {
                response.sendRedirect(request.getContextPath() + "/customers?error=Failed to save customer");
            }
        } catch (MegaCityCabException e) {
            response.sendRedirect(request.getContextPath() + "/customers?error=" + e.getMessage());
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Internal error");
        }
    }
}