package com.epam.lab.paymentsystem.dao;

import com.epam.lab.paymentsystem.entities.User;

@Deprecated
public interface UserDao {

  User createUser(User user);

  User findByLogin(User user);

  User getUserByLogin(String login);
}
