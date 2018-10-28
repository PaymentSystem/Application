package com.epam.lab.paymentsystem.utility.convector;

import com.epam.lab.paymentsystem.entities.User;

public final class Convector {

    private Convector() {
    }

    public static final User convertUser(User user) {
        User convertedUser = new User();
        convertedUser.setLogin(user.getLogin());
        convertedUser.setName(user.getName());
        convertedUser.setPassword(user.getPassword());
        return convertedUser;
    }
}
