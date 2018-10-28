package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.entities.Role;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.repository.RoleRepository;
import com.epam.lab.paymentsystem.repository.UserRepository;
import com.epam.lab.paymentsystem.service.UserService;
import com.epam.lab.paymentsystem.utility.convector.Convector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User addUser(User user) throws LoginAlreadyExistsException {
        User userToAdd = userRepository.findByLogin(user.getLogin());
        if (userToAdd != null) {
            throw new LoginAlreadyExistsException("Login already exists");
        }
        Role role = roleRepository.getRoleByRoleStatus(Roles.USER);
//        System.out.println(role.getId());
        userToAdd = Convector.convertUser(user);
        userToAdd.setRoleId(role.getId());
        userToAdd = userRepository.save(userToAdd);
        return userToAdd;
    }
}
