package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dao.RoleDAO;
import com.epam.lab.paymentsystem.dao.UserDAO;
import com.epam.lab.paymentsystem.dao.impl.UserDAOImpl;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public User addUser(User user) throws LoginAlreadyExistsException {
        User userToAdd = userDAO.findByLogin(user);
        if (userToAdd != null) {
            throw new LoginAlreadyExistsException("Login already exists");
        }
        int role_id = roleDAO.getIdByRole(Roles.USER);
        User userToCreate = UserDAOImpl.getCopy(user);
        userToCreate.setRoleId(role_id);
        userToAdd = userDAO.createUser(userToCreate);
        return userToAdd;
    }

}
