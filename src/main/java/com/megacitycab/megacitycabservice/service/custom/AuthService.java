package com.megacitycab.megacitycabservice.service.custom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    void login(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void register(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void logout(HttpServletRequest request, HttpServletResponse response);

}
