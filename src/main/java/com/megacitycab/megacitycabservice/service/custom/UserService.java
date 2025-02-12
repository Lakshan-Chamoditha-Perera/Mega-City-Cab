package com.megacitycab.megacitycabservice.service.custom;

import com.megacitycab.megacitycabservice.entity.custom.User;
import com.megacitycab.megacitycabservice.service.Service;

import java.util.List;

public interface UserService extends Service {
    List<User> findAll();
}
