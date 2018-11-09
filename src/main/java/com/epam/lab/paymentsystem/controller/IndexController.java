package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the main page, processes the specified URL and redirects
 * the request to the specified page.
 *
 * @author unascribed
 * @since 0.0.1
 */
@Controller
public class IndexController {
  private static final String INDEX_PAGE = "index";
  private static final Logger LOGGER = LogManager.getLogger(IndexController.class);
  private static final String LOGIN_PAGE = "login";

  @Autowired
  private UserService userService;

  /**
   * Returns application main page.
   *
   * @return main page
   */
  @GetMapping(value = "/")
  public String getIndexPage(Model model) {
    LOGGER.info("Return index page");
    model.addAttribute("userLogin", userService.getCurrentUserLogin());
    return INDEX_PAGE;
  }

  @GetMapping(value = "/login")
  public String getLoginPage() {
    LOGGER.info("Return login page");
    return LOGIN_PAGE;
  }
}

