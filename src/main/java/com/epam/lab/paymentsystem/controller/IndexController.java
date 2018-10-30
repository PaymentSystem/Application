package com.epam.lab.paymentsystem.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  private static final String INDEX_PAGE = "index";
  private static final Logger LOGGER = LogManager.getLogger(IndexController.class);
  private static final String LOGIN_PAGE = "login";

  @GetMapping(value = "/")
  public String getIndexPage() {
    LOGGER.info("Return index page");
    return INDEX_PAGE;
  }

  @GetMapping(value = "/login")
  public String getLoginPage() {
    LOGGER.info("Return login page");
    return LOGIN_PAGE;
  }
}
