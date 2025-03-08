package com.megacitycab.megacitycabservice.servlet;

import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.ServiceType;
import com.megacitycab.megacitycabservice.service.custom.BookingService;
import com.megacitycab.megacitycabservice.service.custom.CustomerService;
import com.megacitycab.megacitycabservice.service.custom.DriverService;
import com.megacitycab.megacitycabservice.service.custom.VehicleService;
import com.megacitycab.megacitycabservice.service.factory.ServiceFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "appServlet", value = "/home")
public class MegaCItyWebServlet extends HttpServlet {
    private BookingService bookingService;
    private VehicleService vehicleService;
    private CustomerService customerService;
    private DriverService driverService;

    @Override
    public void init() {
        bookingService = ServiceFactory.getInstance().getService(ServiceType.BOOKING);
        vehicleService = ServiceFactory.getInstance().getService(ServiceType.VEHICLE);
        customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
        driverService = ServiceFactory.getInstance().getService(ServiceType.DRIVER);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            Integer customersCount = customerService.getCustomersCount();
            Integer vehiclesCount = vehicleService.getVehiclesCount();
            Integer driversCount = driverService.getDriversCount();
            Integer bookingsCount = bookingService.getBookingsCount();
            Float totalProfit = bookingService.getTotalProfit();

            request.setAttribute("customersCount", customersCount);
            request.setAttribute("vehiclesCount", vehiclesCount);
            request.setAttribute("driversCount", driversCount);
            request.setAttribute("bookingsCount", bookingsCount);
            request.setAttribute("totalProfit", totalProfit);


            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } catch (MegaCityCabException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home?error=" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Internal error");
        }
    }

    @Override
    public void destroy() {
    }
}
