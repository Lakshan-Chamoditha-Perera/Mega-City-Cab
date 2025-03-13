package com.megacitycab.megacitycabservice.servlet;

import com.megacitycab.megacitycabservice.dto.custom.RevenueReportDTO;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.service.ServiceType;
import com.megacitycab.megacitycabservice.service.custom.BookingService;
import com.megacitycab.megacitycabservice.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/reports")
public class ReportsServlet extends HttpServlet {

    private final BookingService bookingService;

    public ReportsServlet() {
        this.bookingService = ServiceFactory.getInstance().getService(ServiceType.BOOKING);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String reportType = request.getParameter("reportType");
            if (reportType == null || reportType.isEmpty()) {
                reportType = "WEEKLY"; // Default to WEEKLY
            }

            List<RevenueReportDTO> reportData = bookingService.getRevenueReport(reportType);

            request.setAttribute("reportData", reportData);
            request.setAttribute("reportType", reportType);
            request.getRequestDispatcher("/reports.jsp").forward(request, response);

        } catch (MegaCityCabException e) {
            String errorMessage = URLEncoder.encode("Failed to generate report: " + e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=" + errorMessage);
        }
    }
}