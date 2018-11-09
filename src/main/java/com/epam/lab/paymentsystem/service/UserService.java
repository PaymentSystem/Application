package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * UserService is the interface that specifies the service methods.
 */
@Service
public interface UserService {
  /**
   * The root interface in the <i>user service hierarchy</i> Method is the link between the
   * controller and the repository.
   *
   * @param user object from {@code UserController}
   * @return user entity from database
   * @throws LoginAlreadyExistsException if login already exist
   */
  User addUser(UserDto user) throws LoginAlreadyExistsException;

  /**
   * Returns user by passed login.
   *
   * @param login user's login
   * @return user
   */
  User getUserByLogin(String login);

  /**
   * Returns list of all users.
   *
   * @return list
   */
  List<User> getAllUsers();

  /**
   * Returns current logged user's login.
   *
   * @return string
   */
  String getCurrentUserLogin();

  /**
   * Sets passed user's role status to BLOCKED.
   *
   * @param user user
   * @return user
   */
  User blockUser(User user);

  /**
   * Sets passed user's role status to USER.
   *
   * @param user user
   * @return user
   */
  User unblockUser(User user);
}
