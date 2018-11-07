package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import org.springframework.stereotype.Service;

/**
 * UserService is the interface that specifies the service methods.
 */
@Service
public interface UserService {
  /**
   * The root interface in the <i>user service hierarchy</i>
   * Method is the link between the controller and the repository.
   *
   * @param user object from {@code UserController}
   * @return user entity from database
   * @throws LoginAlreadyExistsException if login already exist
   */
  User addUser(UserDto user) throws LoginAlreadyExistsException;

  User getUserByLogin(String login);

  String getCurrentUserLogin();

  User blockUser(User user);

  User unblockUser(User user);
}
