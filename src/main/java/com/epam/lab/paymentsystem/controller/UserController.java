package com.epam.lab.paymentsystem.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends MultiActionController {

    public ModelAndView user(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView modelAndView = new ModelAndView("user");
        return modelAndView;
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        System.out.println(name);

    }

    public ModelAndView retrieveIndex(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }


}
