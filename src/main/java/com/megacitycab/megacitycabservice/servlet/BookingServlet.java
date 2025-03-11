package com.megacitycab.megacitycabservice.servlet;

import com.megacitycab.megacitycabservice.dto.custom.BookingDTO;
import com.megacitycab.megacitycabservice.dto.custom.CustomerDTO;
import com.megacitycab.megacitycabservice.dto.custom.VehicleBookingDetailsDTO;
import com.megacitycab.megacitycabservice.dto.custom.VehicleDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.ServiceType;
import com.megacitycab.megacitycabservice.service.custom.BookingService;
import com.megacitycab.megacitycabservice.service.custom.CustomerService;
import com.megacitycab.megacitycabservice.service.custom.VehicleService;
import com.megacitycab.megacitycabservice.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "bookingServlet", value = "/bookings")
public class BookingServlet extends HttpServlet {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final BookingService bookingService;

    public BookingServlet() {
        customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
        vehicleService = ServiceFactory.getInstance().getService(ServiceType.VEHICLE);
        bookingService = ServiceFactory.getInstance().getService(ServiceType.BOOKING);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            List<CustomerDTO> customers = customerService.getAllCustomers();
            request.setAttribute("customers", customers);

            List<VehicleDTO> vehicles = vehicleService.getAllVehiclesWithDriver();
            request.setAttribute("vehicles", vehicles);

            List<BookingDTO> allBookings = bookingService.getBookingsWithCustomer();
            request.setAttribute("allBookings", allBookings);

            for (int i = 0; i < allBookings.size(); i++) {
                System.out.println(allBookings.get(i));
            }

            request.getRequestDispatcher("/manage_booking.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Error fetching bookings page");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("SAVE".equals(action)) {
            handleBookingSave(request, response);
        } else if ("UPDATE".equals(action)) {
            handleBookingUpdate(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/bookings?error=Invalid action");
        }
    }

    private void handleBookingUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String bookingId = request.getParameter("bookingId");
            String status = request.getParameter("status");
            System.out.println("bookingId: " + bookingId + " status: " + status);

            Boolean isUpdated = bookingService.updateBookingStatus(Integer.parseInt(bookingId), status);

            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/bookings?success=Booking status updated successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/bookings?error=Failed to update booking status.");
            }
        } catch (MegaCityCabException e) {
            response.sendRedirect(request.getContextPath() + "/bookings?error=" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=" + e.getMessage());
        }
    }

    private void handleBookingSave(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String customerId = request.getParameter("customerId");
            String pickupLocation = request.getParameter("pickupLocation");
            String destination = request.getParameter("destination");
            String pickupTime = request.getParameter("pickupTime");
            String status = request.getParameter("status");
            String distance = request.getParameter("distance");
            String discount = request.getParameter("discount");
            String tax = request.getParameter("tax");

            String vehicleIdsJson = request.getParameter("vehicleIds"); // Get JSON string from request
            Integer[] vehicleIdsArray = null;

            if (vehicleIdsJson != null && !vehicleIdsJson.isEmpty()) {
                vehicleIdsJson = vehicleIdsJson.replace("[", "").replace("]", ""); // Remove brackets
                String[] stringArray = vehicleIdsJson.split(","); // Split by comma

                // Convert String[] to Integer[]
                vehicleIdsArray = new Integer[stringArray.length];

                for (int i = 0; i < stringArray.length; i++) {
                    stringArray[i] = stringArray[i].trim().replace("\"", ""); // Remove quotes
                    vehicleIdsArray[i] = Integer.parseInt(stringArray[i]); // Convert to Integer
                }

                // Print the integer array
                System.out.println(Arrays.toString(vehicleIdsArray));
            }


            System.out.println("Booking Save Request Received:");
            System.out.println("Customer ID: " + customerId);
            System.out.println("pickup location: " + pickupLocation);
            System.out.println("destination: " + destination);
            System.out.println("pickup time: " + pickupTime);
            System.out.println("status: " + status);
            System.out.println("distance: " + distance);
            System.out.println("discount: " + discount);
            System.out.println("tax: " + tax);
            System.out.println("Vehicle IDs: " + Arrays.toString(vehicleIdsArray));

            List<VehicleBookingDetailsDTO> vehicleList = new ArrayList<>();
            for (Integer vehicleId : vehicleIdsArray) {
                VehicleBookingDetailsDTO vehicleBookingDetailsDTO = new VehicleBookingDetailsDTO();
                vehicleBookingDetailsDTO.setVehicleId(vehicleId);
                vehicleList.add(vehicleBookingDetailsDTO);
            }


            BookingDTO build = BookingDTO.builder()
                    .customerId(Integer.parseInt(customerId))
                    .pickupLocation(pickupLocation)
                    .destination(destination)
                    .pickupTime(LocalDateTime.parse(pickupTime))
                    .status(status)
                    .distance(Float.parseFloat(distance))
                    .discount(Float.parseFloat(discount))
                    .tax(Float.parseFloat(tax))
                    .vehicleBookingDetailsDTOSList(vehicleList)
                    .addedUserId((Integer) request.getAttribute("userId"))
                    .build();

            Boolean added = bookingService.addBooking(build);
            if (added) {
                response.sendRedirect(request.getContextPath() + "/bookings?success=Booking added successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/bookings?error=Booking failed.");
            }

        } catch (MegaCityCabException e) {
            response.sendRedirect(request.getContextPath() + "/bookings?error=" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=" + e.getMessage());
        }

    }
}
