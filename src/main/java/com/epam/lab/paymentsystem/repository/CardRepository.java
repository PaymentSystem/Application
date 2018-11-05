package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
  Card getCardById(Long id);
}
