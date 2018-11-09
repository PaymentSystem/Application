package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.entities.Role;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.repository.RoleRepository;
import com.epam.lab.paymentsystem.repository.UserRepository;
import com.epam.lab.paymentsystem.service.UserService;
import com.epam.lab.paymentsystem.utility.converter.TransformerToEntity;
import java.util.List;
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

  /**
   * Returns user by passed login.
   *
   * @param login user's login
   * @return user
   */
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

  /**
   * Takes BLOCKED role status from {@code RoleRepository}
   * and sends update to {@code UserRepository}.
   *
   * @param user user
   * @return user
   */
  @Override
  public User blockUser(User user) {
    Role role = roleRepository.getRoleByRoleStatus(Roles.BLOCKED);
    user.setRole(role);
    return userRepository.save(user);
  }

  /**
   * Takes USER role status from {@code RoleRepository}
   * and sends update to {@code UserRepository}.
   *
   * @param user user
   * @return user
   */
  @Override
  public User unblockUser(User user) {
    Role role = roleRepository.getRoleByRoleStatus(Roles.USER);
    user.setRole(role);
    return userRepository.save(user);
  }

  /**
   * Returns list of all users given by {@code UserRepository}.
   *
   * @return list
   */
  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}
