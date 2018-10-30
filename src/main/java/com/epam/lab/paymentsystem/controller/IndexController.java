package com.epam.lab.paymentsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  private static final String INDEX_PAGE = "index";
  private static final String LOGIN_PAGE = "login";

  @GetMapping(value = "/")
  public String getIndexPage() {
    return INDEX_PAGE;
  }

  @GetMapping(value = "/login")
  public String getLoginPage() {
    return LOGIN_PAGE;
  }
}
