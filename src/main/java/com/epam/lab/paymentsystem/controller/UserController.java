package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.service.UserService;
import com.epam.lab.paymentsystem.utility.converter.CurrentUser;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

  /**
   * Instance of {@code UserService} injects by Spring.
   */
  @Autowired
  private UserService userService;


  @GetMapping(value = "/registration")
  public String getRegistrationPage() {
    return REGISTRATION_PAGE;
  }

  /**
   * This method takes {@link RequestParam} from the page forms, processes them
   * and sends them to the service.
   *
   * @param userName     name of user from view form
   * @param userLogin    login of user from view form
   * @param userPassword password of user from view form
   * @param model        model
   * @return HTML view
   */
  @PostMapping(value = "/addUser")
  public String addUser(@RequestParam(name = "name") String userName,
                        @RequestParam(name = "login") String userLogin,
                        @RequestParam(name = "password") String userPassword,
                        Model model) {

    UserDto userDto = new UserDto();
    userDto.setName(userName);
    userDto.setLogin(userLogin);
    userDto.setPassword(userPassword);

    try {
      userService.addUser(userDto);
    } catch (LoginAlreadyExistsException e) {
      LOGGER.error("Exception in addUser in UserController", e);
      model.addAttribute("messageException", e.getMessage());
      return REGISTRATION_PAGE;
    }
    return REDIRECT_TO + LOGIN_PAGE;
  }
}
