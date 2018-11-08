package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.UserService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for User,
 * processes the specified URL and redirects
 * the request to the specified page.
 *
 * @author unascribed
 * @since 0.0.1
 */
@Controller
public class UserController {
  private static final Logger LOGGER = LogManager.getLogger(UserController.class);
  private static final String REGISTRATION_PAGE = "registration";
  private static final String LOGIN_PAGE = "login";
  private static final String REDIRECT_TO = "redirect:";
  private static final String USER_PAGE = "user";

  /**
   * Instance of {@code UserService} injects by Spring.
   */
  @Autowired
  private UserService userService;

  @Autowired
  private AccountService accountService;


  @GetMapping(value = "/registration")
  public String getRegistrationPage(Model model) {
    model.addAttribute("userDto", new UserDto());
    return REGISTRATION_PAGE;
  }

  /**
   * Returns user page with list of all accounts linked to that user.
   *
   * @param model model
   * @return user page view
   */
  @GetMapping(value = "/{userLogin}")
  public String getUserPage(@PathVariable(name = "userLogin") String login, Model model) {
    List<Account> accounts = accountService.getAllAccountsOfUser(login);
    model.addAttribute("accountList", accounts);
    String currentUserLogin = userService.getCurrentUserLogin();
    User currentUser = userService.getUserByLogin(currentUserLogin);
    model.addAttribute("currentUser", currentUser);
    User userOnPage = userService.getUserByLogin(login);
    model.addAttribute("userOnPage", userOnPage);

    List<User> users = userService.getAllUsers();
    model.addAttribute("users", users);

    return USER_PAGE;
  }

  /**
   * This method takes {@link ModelAttribute} from the page forms, processes them
   * and sends them to the service.
   *
   * @param userDto userDto
   * @param model   model
   * @return HTML view
   */
  @PostMapping(value = "/addUser")
  public String addUser(@ModelAttribute(name = "userDto") UserDto userDto,
                        Model model) {

    try {
      userService.addUser(userDto);
    } catch (LoginAlreadyExistsException e) {
      LOGGER.error("Exception in addUser in UserController", e);
      model.addAttribute("messageException", e.getMessage());
      return REGISTRATION_PAGE;
    }
    return REDIRECT_TO + LOGIN_PAGE;
  }

  @PostMapping(value = "/{userLogin}/block")
  public String blockUser(@PathVariable(name = "userLogin") String userLogin) {
    userService.blockUser(userService.getUserByLogin(userLogin));
    return REDIRECT_TO + "/{userLogin}";
  }

  @PostMapping(value = "/{userLogin}/unblock")
  public String unblockUser(@PathVariable(name = "userLogin") String userLogin) {
    userService.unblockUser(userService.getUserByLogin(userLogin));
    return REDIRECT_TO + "/{userLogin}";
  }
}
