package com.epam.lab.paymentsystem.utility.impl;

import com.epam.lab.paymentsystem.utility.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Util class for retrieving information about current logged user.
 */
@Component
public class CurrentUserImpl implements CurrentUser {

  private static CurrentUserImpl instance;

  private CurrentUserImpl() {
  }

  /**
   * Return singleton instance of CurrentUser class.
   *
   * @return CurrentUser
   */
  public static CurrentUserImpl getInstance() {
    if (instance == null) {
      instance = new CurrentUserImpl();
    }
    return instance;
  }

  /**
   * Returns current logged user's login.
   *
   * @return login
   */
  public String getCurrentUserLogin() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    return authentication.getName();
  }
}
