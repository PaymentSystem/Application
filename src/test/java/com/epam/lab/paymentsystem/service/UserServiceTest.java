package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.entities.Role;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.repository.RoleRepository;
import com.epam.lab.paymentsystem.repository.UserRepository;
import com.epam.lab.paymentsystem.service.impl.UserServiceImpl;
import com.epam.lab.paymentsystem.utility.converter.TransformerToDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private User user;
  private UserDto userDto;
  @Mock
  private RoleRepository roleRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  public void testSetUp() {
    user = new User();
    user.setLogin("test");
    user.setPassword("testPassword");
    userDto = TransformerToDto.convertUser(user);
  }

  @Test
  public void testAddUserThrowsException() {
    when(userRepository.getUserByLogin(user.getLogin())).thenReturn(user);
    assertThrows(LoginAlreadyExistsException.class,
        () -> userService.addUser(userDto),
        "Login already exists");
  }

  @Test
  public void testAddUserCreateUser() throws LoginAlreadyExistsException {
    when(roleRepository.getRoleByRoleStatus(Roles.USER)).thenReturn(new Role());
    when(userRepository.getUserByLogin(user.getLogin())).thenReturn(null);
    when(userRepository.save(user)).thenReturn(user);
    assertEquals(user, userService.addUser(userDto));
  }
}
