//package com.epam.lab.paymentsystem.service.impl;
//
//import com.epam.lab.paymentsystem.entities.User;
//import com.epam.lab.paymentsystem.service.UserService;
//
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//
//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public User addUser(User user) {
//        User userToAdd = userRepository.findByLogin(user);
//        if (userToAdd != null) {
//            throw new UnsupportedOperationException("Login already exists");
//        }
//        userToAdd = userRepository.createUser(user);
//        return userToAdd;
//    }
//}
