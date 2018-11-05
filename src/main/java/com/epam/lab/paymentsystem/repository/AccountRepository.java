package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
