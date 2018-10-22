package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dao.UserDAOInterface;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDAOInterface userDAO;

    public UserDetailsServiceImpl(UserDAOInterface userDAO) {
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
