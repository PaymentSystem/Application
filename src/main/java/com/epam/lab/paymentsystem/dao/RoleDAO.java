package com.epam.lab.paymentsystem.dao;

import com.epam.lab.paymentsystem.entities.enums.Roles;

public interface RoleDAO {
    int getIdByRole(Roles roles);
}
