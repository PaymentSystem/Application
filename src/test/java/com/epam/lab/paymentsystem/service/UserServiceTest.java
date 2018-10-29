package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dao.RoleDao;
import com.epam.lab.paymentsystem.dao.UserDao;
import com.epam.lab.paymentsystem.dao.impl.RoleDaoImpl;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  private User user;
  @Mock
  private UserDao userDAO;
  @Mock
  private RoleDao roleDao;
  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  public void testSetUp() {
    user = new User();
    user.setLogin("test");
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
