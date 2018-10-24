package com.epam.lab.paymentsystem.dao;

import com.epam.lab.paymentsystem.entities.enums.Roles;
import org.springframework.stereotype.Component;

@Component
public interface RoleDAO {
    int getIdByRole(Roles roles);
}
