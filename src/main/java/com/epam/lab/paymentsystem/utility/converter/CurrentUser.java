package com.epam.lab.paymentsystem.utility.converter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Util class for retrieving information about current logged user.
 */
public class CurrentUser {
  /**
   * Returns current logged user's login.
   *
   * @return login
   */
  public static String getCurrentUserLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }
}
