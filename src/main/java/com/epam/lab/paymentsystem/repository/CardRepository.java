package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.User;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for generic CRUD operations on a repository for a specific type. Card specific
 * extension of JPA.
 *
 * @author unascribed
 * @link org.springframework.data.jpa.repository
 * @link org.springframework.data.repository
 * @since 0.0.1
 */
public interface CardRepository extends JpaRepository<Card, Long> {

  /**
   * Returns card form database by passed id.
   *
   * @param account account
   * @param pageable pageable
   * @return list of cards by account
   */
  Page<Card> getAllByAccount(Account account, Pageable pageable);

  /**
   * Returns card form database by passed id.
   *
   * @param account account
   * @return list of cards by account
   */
  List<Card> getAllByAccount(Account account);

  /**
   * Returns card form database by passed user.
   *
   * @param user user
   * @return list of cards by user
   */
  List<Card> getAllByUser(User user);

  /**
   * Returns card form database by passed user.
   *
   * @param user user
   * @param pageable pageable
   * @return page of cards by user
   */
  Page<Card> getAllByUser(User user, Pageable pageable);

  /**
   * Returns card form database by passed id.
   *
   * @param id id of card
   * @return card entity
   */
  Card getCardById(long id);
}
