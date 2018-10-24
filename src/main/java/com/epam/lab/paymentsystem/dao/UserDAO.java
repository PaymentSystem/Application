package com.epam.lab.paymentsystem.dao;

import com.epam.lab.paymentsystem.entities.User;
import org.springframework.stereotype.Component;

@Component
public interface UserDAO {

    User createUser(User user);

    User findByLogin(User user);

    User getUserByLogin(String login);
}
