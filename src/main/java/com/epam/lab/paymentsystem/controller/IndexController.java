package com.epam.lab.paymentsystem.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexController extends AbstractController {

    private final String INDEX_PAGE = "index";

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView modelAndView = new ModelAndView(INDEX_PAGE);
        modelAndView.addObject("msg", "Hello world!");
        return modelAndView;
    }
}
