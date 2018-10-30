package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dao.RoleDao;
import com.epam.lab.paymentsystem.dao.UserDao;
import com.epam.lab.paymentsystem.dao.impl.UserDaoImpl;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
  private UserDao userDao;
  private RoleDao roleDao;

  public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
    this.userDao = userDao;
    this.roleDao = roleDao;
  }

  @Override
  public User addUser(User user) throws LoginAlreadyExistsException {
    User userToAdd = userDao.findByLogin(user);
    if (userToAdd != null) {
      LOGGER.error("LoginAlreadyExistsException in UserServiceImpl in addUser method");
      throw new LoginAlreadyExistsException("Login already exists");
    }
    int roleId = roleDao.getIdByRole(Roles.USER);
    User userToCreate = UserDaoImpl.getCopy(user);
    userToCreate.setRoleId(roleId);
    userToCreate.setPassword(new BCryptPasswordEncoder().encode(userToCreate.getPassword()));
    userToAdd = userDao.createUser(userToCreate);
    return userToAdd;
  }
}
