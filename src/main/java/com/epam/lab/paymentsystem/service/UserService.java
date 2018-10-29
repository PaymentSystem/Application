package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;

public interface UserService {
  User addUser(User user) throws LoginAlreadyExistsException;
}
