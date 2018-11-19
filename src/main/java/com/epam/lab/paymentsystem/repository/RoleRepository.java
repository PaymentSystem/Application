package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.Role;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 * Role specific extension of JPA.
 *
 * @author unascribed
 * @link org.springframework.data.jpa.repository
 * @link org.springframework.data.repository
 * @since 0.0.1
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
  /**
   * Returns the role by role status from repository.
   *
   * @param role Enum variable
   * @return Role entity
   */
  Role getRoleByRoleStatus(Roles role);
}
