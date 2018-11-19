package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 * User specific extension of JPA.
 *
 * @author unascribed
 * @link org.springframework.data.jpa.repository
 * @link org.springframework.data.repository
 * @since 0.0.1
 */
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Returns the login from repository for user.
   *
   * @param login String variable from user service
   * @return user entity
   */
  User getUserByLogin(String login);
}
