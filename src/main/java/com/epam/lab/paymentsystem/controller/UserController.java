package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private static final String REGISTRATION_PAGE = "registration";
    private static final String REDIRECT_TO = "redirect:";
    private static final String ROOT = "/";

    @Autowired
    private UserService userService;

    @GetMapping(value = "/registration")
    public String getRegistrationPage() {
        return REGISTRATION_PAGE;
    }

    @PostMapping(value = "/addUser")
    public String addUser(@RequestParam(name = "name") String userName,
                          @RequestParam(name = "login") String userLogin,
                          @RequestParam(name = "password") String userPassword) {

        User user = new User();
        user.setName(userName);
        user.setLogin(userLogin);
        user.setPassword(userPassword);

        try {
            userService.addUser(user);
        } catch (LoginAlreadyExistsException e) {
            return REDIRECT_TO + ROOT;
        }
        return REGISTRATION_PAGE;
    }
}
