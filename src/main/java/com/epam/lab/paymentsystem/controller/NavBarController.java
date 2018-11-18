package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the navigation bar, processes the specified URL and redirects
 * the request to the specified page.
 *
 * @author unascribed
 * @since 0.0.1
 */
@Controller
public class NavBarController {
  private static final String REDIRECT_TO = "redirect:";

  /**
   * Instance of {@code UserService} injects by Spring.
   */
  @Autowired
  private UserService userService;

  /**
   * Returns user cards page with list of all cards linked to that user.
   *
   * @return URL path to the user cards page
   */
  @GetMapping(value = "/myCards")
  public String getUserCards() {
    String currentUserLogin = userService.getCurrentUserLogin();
    return REDIRECT_TO + "/" + currentUserLogin + "/cards";
  }

  /**
   * Returns user history page with list of all operations linked to that user.
   *
   * @return URL path to the user history page
   */
  @GetMapping(value = "/history")
  public String getUserHistory() {
    String currentUserLogin = userService.getCurrentUserLogin();
    return REDIRECT_TO + "/" + currentUserLogin + "/history";
  }

  /**
   * Returns user accounts page with list of all accounts linked to that user.
   *
   * @return URL path to the user account page
   */
  @GetMapping(value = "/my")
  public String getUserAccounts() {
    String currentUserLogin = userService.getCurrentUserLogin();
    return REDIRECT_TO + "/" + currentUserLogin;
  }
}
