package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for generic CRUD operations on a repository for a specific type. Operation specific
 * extension of JPA.
 *
 * @author unascribed
 * @link org.springframework.data.jpa.repository
 * @link org.springframework.data.repository
 * @since 0.0.1
 */
public interface OperationRepository extends JpaRepository<Operation, Long> {
  /**
   * Returns list of operation from database which are linked to passed card id.
   *
   * @param cardId long entity
   * @return list of operation entities
   */
  List<Operation> getAllBySourceCardId(long cardId);

  /**
   * Returns list of operation from database which are linked to passed list card.
   *
   * @param cards list entity
   * @return list of operation entities
   */
  List<Operation> getAllBySourceCardIsIn(List<Card> cards);
}
