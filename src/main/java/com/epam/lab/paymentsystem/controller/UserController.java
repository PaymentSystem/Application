package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private static final String REGISTRATION_PAGE = "registration";
    private static final String ROOT = "redirect:/";


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistrationPage(){
        return REGISTRATION_PAGE;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestParam(name = "name") String userName,
                          @RequestParam(name = "login") String userLogin,
                          @RequestParam(name = "password") String userPassword){

        User user = new User();
        user.setName(userName);
        user.setLogin(userLogin);
        user.setPassword(userPassword);

        try {
            userService.addUser(user);
        } catch (LoginAlreadyExistsException e) {
            return ROOT;
        }
        return REGISTRATION_PAGE;
    }
}
