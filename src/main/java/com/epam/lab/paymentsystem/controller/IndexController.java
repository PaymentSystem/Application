package com.epam.lab.paymentsystem.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final Logger LOGGER = Logger.getLogger(IndexController.class);

    private static final String INDEX_PAGE = "index";

    @GetMapping(value = "/")
    public String getIndexPage() {
        LOGGER.debug("Return index page");
        return INDEX_PAGE;
    }

}