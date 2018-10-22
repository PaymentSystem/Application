package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController {

    private static final String REGISTRATION_PAGE = "registration";
    private UserService userService;

    public String getRegistration(HttpServletRequest req, HttpServletResponse resp) {
        return REGISTRATION_PAGE;
    }

    public String add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        try {
            userService.addUser(user);
            resp.sendRedirect("/");
        } catch (LoginAlreadyExistsException e) {
            req.setAttribute("submit", e.getMessage());
        }
        return REGISTRATION_PAGE;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
