package com.epam.lab.paymentsystem.controller;

import org.springframework.stereotype.Controller;
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

  /**
   * Returns application main page.
   *
   * @return main page
   */
  @GetMapping(value = "/")
  public String getIndexPage() {
    return INDEX_PAGE;
  }
}

