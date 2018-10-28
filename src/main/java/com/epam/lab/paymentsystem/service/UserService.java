package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
  User addUser(User user) throws LoginAlreadyExistsException;
}
