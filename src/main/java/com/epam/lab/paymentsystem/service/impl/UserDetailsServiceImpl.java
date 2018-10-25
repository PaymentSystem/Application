package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dao.UserDAO;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDAO userDAO;

    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDAO.getUserByLogin(login);
        if(user == null) {
            throw new UsernameNotFoundException(login);
        }
        return new MyUserDetails(user);
    }
}
