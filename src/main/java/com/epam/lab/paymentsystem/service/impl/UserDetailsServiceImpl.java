package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.entities.MyUserDetails;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    User user = userRepository.getUserByLogin(login);
    if (user == null) {
      throw new UsernameNotFoundException(login);
    }
    return new MyUserDetails(user);
  }
}