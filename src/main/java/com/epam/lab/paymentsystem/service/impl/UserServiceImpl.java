package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dao.UserDAOInterface;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserDAOInterface userDAO;

    public UserServiceImpl(UserDAOInterface userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User addUser(User user) throws LoginAlreadyExistsException {
        User userToAdd = userDAO.findByLogin(user);
        if (userToAdd != null) {
            throw new LoginAlreadyExistsException("Login already exists");
        }
        userToAdd = userDAO.createUser(user);
        return userToAdd;
    }

}
