package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {

  List<Operation> getAllBySourceCardId(long cardId);

}
