package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.entities.Role;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.repository.RoleRepository;
import com.epam.lab.paymentsystem.repository.UserRepository;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.UserService;
import com.epam.lab.paymentsystem.utility.converter.TransformerToEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class provides implementation of the <tt>UserService</tt>, provides methods to manipulate
 * the data using repositories methods. The class is designed to implement business logic.
 */
@Service
public class UserServiceImpl implements UserService {
  private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
  /**
   * Instance of {@code UserRepository} injects by Spring.
   */
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AccountService accountService;

  /**
   * Instance of {@code RoleRepository} injects by Spring.
   */
  @Autowired
  private RoleRepository roleRepository;

  /**
   * This method add user into repository via {@code UserRepository}, method checks login from the
   * database and if login does not exist then adds user, method can throws
   * LoginAlreadyExistsException if login already exist, uses method to convert user from {@code
   * Controller} to new user object.
   *
   * @param userDto object from {@code UserController}
   * @return user entity from repository
   * @throws LoginAlreadyExistsException if login already exist
   * @see com.epam.lab.paymentsystem.controller
   * @see com.epam.lab.paymentsystem.utility.converter
   */
  @Override
  public User addUser(UserDto userDto) throws LoginAlreadyExistsException {
    User userToAdd = userRepository.getUserByLogin(userDto.getLogin());
    if (userToAdd != null) {
      LOGGER.error("LoginAlreadyExistsException in UserServiceImpl in addUser method");
      throw new LoginAlreadyExistsException("Login already exists");
    }
    Role role = roleRepository.getRoleByRoleStatus(Roles.USER);
    userToAdd = TransformerToEntity.convertUser(userDto);
    userToAdd.setRole(role);
    userToAdd.setPassword(new BCryptPasswordEncoder().encode(userToAdd.getPassword()));
    userToAdd = userRepository.save(userToAdd);
    return userToAdd;
  }

  @Override
  public User getUserByLogin(String login) {
    return userRepository.getUserByLogin(login);
  }

  /**
   * Returns current logged user's login.
   *
   * @return login
   */
  @Override
  public String getCurrentUserLogin() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    return authentication.getName();
  }
}
