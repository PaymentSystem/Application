package com.epam.lab.paymentsystem.dao;

import com.epam.lab.paymentsystem.entities.User;

public interface UserDAOInterface {

    User createUser(User user);

    User findByLogin(User user);
}
