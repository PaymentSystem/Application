package com.epam.lab.paymentsystem.repository;


import com.epam.lab.paymentsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByLogin(String login);

  User getUserByLogin(String login);
}
