package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
