package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.entities.Role;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.repository.RoleRepository;
import com.epam.lab.paymentsystem.repository.UserRepository;
import com.epam.lab.paymentsystem.service.UserService;
import com.epam.lab.paymentsystem.utility.converter.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class provides implementation of the <tt>UserService</tt>, provides methods
 * to manipulate the data using repositories methods. The class is designed to implement
 * business logic.
 */
@Service
public class UserServiceImpl implements UserService {
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
   * This method add user into repository via {@code UserRepository}, method checks login from
   * the database and if login does not exist then adds user, method can
   * throws LoginAlreadyExistsException if login already exist, uses method to convert user
   * from {@code Controller} to new user object.
   *
   * @param user object from {@code UserController}
   * @return user entity from repository
   * @throws LoginAlreadyExistsException if login already exist
   * @see com.epam.lab.paymentsystem.controller
   * @see com.epam.lab.paymentsystem.utility.converter
   */
  @Override
  public User addUser(User user) throws LoginAlreadyExistsException {
    User userToAdd = userRepository.getUserByLogin(user.getLogin());
    if (userToAdd != null) {
      throw new LoginAlreadyExistsException("Login already exists");
    }
    Role role = roleRepository.getRoleByRoleStatus(Roles.USER);
    userToAdd = Transformer.convertUser(user);
    userToAdd.setRoleId(role.getId());
    userToAdd = userRepository.save(userToAdd);
    return userToAdd;
  }
}
