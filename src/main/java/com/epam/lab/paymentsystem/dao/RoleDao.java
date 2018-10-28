package com.epam.lab.paymentsystem.dao;

import com.epam.lab.paymentsystem.entities.enums.Roles;
@Deprecated
public interface RoleDao {
    int getIdByRole(Roles roles);
}
