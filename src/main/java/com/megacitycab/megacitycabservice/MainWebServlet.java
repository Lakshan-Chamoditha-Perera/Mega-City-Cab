package com.megacitycab.megacitycabservice;

import java.io.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "appServlet", value = "/app")
public class MainWebServlet extends HttpServlet {

    private String message;

    @Override
    public void init() {
        // Initialize the message
        message = "Instance is up";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Instance Status</title></head>");
        out.println("<body>");
        out.println("<h1 style='color: green; text-align: center;'>" + message + "</h1>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    public void destroy() {
      }
}
