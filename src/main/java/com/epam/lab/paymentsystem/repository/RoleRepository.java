package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.Role;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getRoleByRoleStatus(Roles role);
}
