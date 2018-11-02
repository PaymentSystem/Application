package com.epam.lab.paymentsystem.utility.converter;

import com.epam.lab.paymentsystem.entities.User;

/**
 * Utility class provides data transfer between Web and the business layer.
 * All object are transformed by this class before they save in the repository.
 */
public final class Transformer {

  private Transformer() {
  }

  /**
   * Creates a copy of the object from Web.
   *
   * @param user from jsp page
   * @return new user object
   */
  public static final User convertUser(User user) {
    User convertedUser = new User();
    convertedUser.setLogin(user.getLogin());
    convertedUser.setName(user.getName());
    convertedUser.setPassword(user.getPassword());
    return convertedUser;
  }
}
