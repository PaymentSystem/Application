package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dao.UserDAOInterface;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private static User user;
    private static UserDAOInterface userDAO;

    @Test
    public void testAddUserThrowsException() {

        user = new User();
        userDAO = mock(UserDAOInterface.class);
        when(userDAO.findByLogin(user)).thenReturn(user);
        UserServiceImpl userService = new UserServiceImpl(userDAO);
        assertThrows(UnsupportedOperationException.class, () -> userService.addUser(user), "Login already exists");
    }

    @Test
    public void testAddUserCreateUser() {
        user = new User();
        userDAO = mock(UserDAOInterface.class);
        when(userDAO.findByLogin(user)).thenReturn(null);
        when(userDAO.createUser(user)).thenReturn(user);
        UserServiceImpl userService = new UserServiceImpl(userDAO);
        assertEquals(user, userService.addUser(user));
    }
}
