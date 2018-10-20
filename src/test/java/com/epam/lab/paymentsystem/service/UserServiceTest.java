package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dao.RoleDAO;
import com.epam.lab.paymentsystem.dao.UserDAO;
import com.epam.lab.paymentsystem.dao.impl.RoleDAOImpl;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private User user;
    private UserDAO userDAO;
    private UserServiceImpl userService;

    @BeforeEach
    public void testSetUp() {
        user = new User();
        user.setLogin("test");
        userDAO = mock(UserDAO.class);
        RoleDAO roleDAO = new RoleDAOImpl();
        userService = new UserServiceImpl(userDAO, roleDAO);
    }

    @Test
    public void testAddUserThrowsException() {
        when(userDAO.findByLogin(user)).thenReturn(user);
        assertThrows(LoginAlreadyExistsException.class,
                () -> userService.addUser(user),
                "Login already exists");
    }

    @Test
    public void testAddUserCreateUser() throws LoginAlreadyExistsException {
        when(userDAO.findByLogin(user)).thenReturn(null);
        when(userDAO.createUser(user)).thenReturn(user);
        assertEquals(user, userService.addUser(user));
    }
}