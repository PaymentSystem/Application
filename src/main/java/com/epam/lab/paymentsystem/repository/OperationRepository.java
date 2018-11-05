package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {

  Account getAccountByCard(Card card);

  Operation save(Operation operation);
}
