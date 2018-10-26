package com.epam.lab.paymentsystem.dao;

import com.epam.lab.paymentsystem.entities.enums.Roles;

public interface RoleDao {
  int getIdByRole(Roles roles);
}
