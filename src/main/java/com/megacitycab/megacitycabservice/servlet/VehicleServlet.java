package com.megacitycab.megacitycabservice.servlet;

import com.megacitycab.megacitycabservice.dto.custom.DriverDTO;
import com.megacitycab.megacitycabservice.dto.custom.VehicleDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.ServiceType;
import com.megacitycab.megacitycabservice.service.custom.DriverService;
import com.megacitycab.megacitycabservice.service.custom.VehicleService;
import com.megacitycab.megacitycabservice.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "vehicleServlet", value = "/vehicles")
public class VehicleServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(VehicleServlet.class.getName());
    private VehicleService vehicleService;
    private DriverService driverService;

    @Override
    public void init() {
        vehicleService = ServiceFactory.getInstance().getService(ServiceType.VEHICLE);
        driverService = ServiceFactory.getInstance().getService(ServiceType.DRIVER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("Handling GET request for Vehicles");

        try {
            List<VehicleDTO> vehicles = vehicleService.getAllVehiclesWithDriver();
            List<DriverDTO> drivers = driverService.getAllAvailableDriversForVehicle();
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("drivers", drivers);
            request.getRequestDispatcher("/manage_vehicle.jsp").forward(request, response);
        } catch (MegaCityCabException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vehicles?error=" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Internal error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("SAVE".equals(action)) {
            handleVehicleSave(request, response);
        } else if ("DELETE".equals(action)) {
            handleVehicleDelete(request, response);
        } else if ("UPDATE".equals(action)) {
            handleVehicleUpdate(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/vehicles?error=Invalid action");
        }
    }

    private void handleVehicleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Handling POST request to update vehicle");
        try {
            String driverId = request.getParameter("driverId");
            VehicleDTO vehicleDTO = VehicleDTO.builder()
                    .vehicleId(Integer.parseInt(request.getParameter("vehicleId")))
                    .licensePlate(request.getParameter("licensePlate"))
                    .model(request.getParameter("model"))
                    .brand(request.getParameter("brand"))
                    .passengerCount(Integer.parseInt(request.getParameter("passengerCount")))
                    .color(request.getParameter("color"))
                    .availability(Boolean.parseBoolean(request.getParameter("availability")))
                    .driverId((driverId == null || driverId.isEmpty()) ? 0 : Integer.parseInt(driverId))
                    .pricePerKm(Float.parseFloat(request.getParameter("pricePerKm")))
                    .build();

            boolean success = vehicleService.updateVehicle(vehicleDTO);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/vehicles?success=Vehicle updated successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/vehicles?error=Failed to update vehicle");
            }
        } catch (MegaCityCabException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vehicles?error=" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Internal error");
        }
    }


    private void handleVehicleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vehicleId = request.getParameter("vehicleId");
        logger.info("Handling DELETE request for vehicle ID: " + vehicleId);

        try {
            boolean success = vehicleService.deleteVehicle(Integer.parseInt(vehicleId));
            if (success) {
                response.sendRedirect(request.getContextPath() + "/vehicles?success=Vehicle successfully deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/vehicles?error=Failed to delete vehicle");
            }
        } catch (MegaCityCabException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vehicles?error=" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Internal error");
        }
    }

    private void handleVehicleSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Handling POST request to save vehicle");
        try {
            boolean success = vehicleService.saveVehicle(
                    VehicleDTO.builder()
                            .licensePlate(request.getParameter("licensePlate"))
                            .model(request.getParameter("model"))
                            .brand(request.getParameter("brand"))
                            .passengerCount(Integer.parseInt(request.getParameter("passengerCount")))
                            .color(request.getParameter("color"))
                            .availability(Boolean.valueOf(request.getParameter("availability")))
                            .driverId(Integer.parseInt(request.getParameter("driverId")))
                            .addedUserId((Integer) request.getAttribute("userId"))
                            .pricePerKm(Float.parseFloat(request.getParameter("pricePerKm")))
                            .build()
            );


            if (success) {
                response.sendRedirect(request.getContextPath() + "/vehicles?success=Vehicles successfully saved");
            } else {
                response.sendRedirect(request.getContextPath() + "/vehicles?error=Failed to save vehicles");
            }
        } catch (MegaCityCabException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vehicles?error=" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Internal error");
        }
    }

}
