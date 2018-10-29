package com.epam.lab.paymentsystem.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  private static final String INDEX_PAGE = "index";
  private static final Logger LOGGER = LogManager.getLogger(IndexController.class);

  @GetMapping(value = "/")
  public String getIndexPage() {
    LOGGER.info("Return index page");
    return INDEX_PAGE;
  }
}
