package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.dao.UserDAO;
import com.epam.lab.paymentsystem.service.UserService;

public class UserServiceImpl implements UserService {


    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User addUser(User user) {
        User userToAdd = userDAO.findByLogin(user);
        if (userToAdd != null) {
            throw new UnsupportedOperationException("Login already exists");
        }
        userToAdd = userDAO.createUser(user);
        return userToAdd;
    }

}
