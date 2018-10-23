package com.epam.lab.paymentsystem.configuration;

import com.epam.lab.paymentsystem.dao.RoleDAO;
import com.epam.lab.paymentsystem.dao.UserDAO;
import com.epam.lab.paymentsystem.dao.impl.RoleDAOImpl;
import com.epam.lab.paymentsystem.dao.impl.UserDAOImpl;
import com.epam.lab.paymentsystem.service.UserService;
import com.epam.lab.paymentsystem.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    @Bean
    public RoleDAO getRoleDAO() {
        return new RoleDAOImpl();
    }

    @Bean
    public UserService getUserService(UserDAO userDAO, RoleDAO roleDAO) {
        return new UserServiceImpl(userDAO, roleDAO);
    }
}
