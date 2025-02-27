package com.megacitycab.megacitycabservice.service.custom.impl;

import com.megacitycab.megacitycabservice.entity.custom.User;
import com.megacitycab.megacitycabservice.exception.MegaCityCabException;
import com.megacitycab.megacitycabservice.repository.custom.UserRepository;
import com.megacitycab.megacitycabservice.repository.custom.impl.UserRepositoryImpl;
import com.megacitycab.megacitycabservice.service.custom.UserService;
import com.megacitycab.megacitycabservice.util.TransactionManager;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final TransactionManager transactionManager;
    private final UserRepository userRepository;

    public UserServiceImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        userRepository = new UserRepositoryImpl();
    }


    public void save(User user) throws MegaCityCabException {
        transactionManager.doInTransaction(connection -> {
            return userRepository.save(user, connection);
        });
    }

    public List<User> findAll() throws RuntimeException, MegaCityCabException {
            List<User> users = transactionManager.doReadOnly(connection -> {
                return userRepository.findAll(connection);
            });
            return users;
    }

}
