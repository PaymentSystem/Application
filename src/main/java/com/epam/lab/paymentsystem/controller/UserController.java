package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController {

    private static final String USER_PAGE = "user";
    private UserService userService;

    public String getUser(HttpServletRequest req, HttpServletResponse resp) {
        return USER_PAGE;
    }

    public String add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        userService.addUser(user);

        return USER_PAGE;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
