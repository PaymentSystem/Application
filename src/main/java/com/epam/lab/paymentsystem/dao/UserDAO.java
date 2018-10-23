package com.epam.lab.paymentsystem.dao;

import com.epam.lab.paymentsystem.entities.User;

public interface UserDAO {

    User createUser(User user);

    User findByLogin(User user);

    User getUserByLogin(String login);
}
