package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.service.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService extends Service {

    void login(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void register(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void logout(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
