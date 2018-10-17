package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController {

    UserServiceImpl userService;

    public String getUser(HttpServletRequest req, HttpServletResponse resp) {
        return "user";
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

        return "user";
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }
}
