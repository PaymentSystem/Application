package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dao.UserDao;
import com.epam.lab.paymentsystem.entities.MyUserDetails;
import com.epam.lab.paymentsystem.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserDao userDao;

  public UserDetailsServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    User user = userDao.getUserByLogin(login);
    if (user == null) {
      throw new UsernameNotFoundException(login);
    }
    return new MyUserDetails(user);
  }
}
