package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.Account;
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
   * The root interface in the <i>user service hierarchy</i>
   * Method is the link between the controller and the repository.
   *
   * @param user object from {@code UserController}
   * @return user entity from database
   * @throws LoginAlreadyExistsException if login already exist
   */
  User addUser(User user) throws LoginAlreadyExistsException;

  List<Account> getAllAccountsByLogin(String login);

  User getUserByLogin(String login);
}
