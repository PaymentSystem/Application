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
   * @param cardSrcId long src card id
   * @param cardDstId long dst card id
   * @return list of operation entities
   */
  List<Operation> getAllBySourceCardIdOrTargetCardId(long cardSrcId, long cardDstId);

  /**
   * Returns list of operation from database which are linked to passed list card.
   *
   * @param cardsSrc list cards src entity
   * @param cardsDst list cards dst entity
   * @return list of operation entities
   */
  List<Operation> getAllBySourceCardIsInOrTargetCardIsIn(List<Card> cardsSrc, List<Card> cardsDst);
}
