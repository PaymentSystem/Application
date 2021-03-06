package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Card;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * CardService is the interface that specifies the service methods.
 */
public interface CardService {

  /**
   * Returns page of cards by given account id.
   *
   * @param id       account id
   * @param pageable pageable
   * @return page of cards
   */
  Page<Card> getAllCardsByAccountId(long id, Pageable pageable);

  /**
   * Returns list of cards by given account id.
   *
   * @param id account id
   * @return list of cards
   */
  List<Card> getAllCardsByAccountId(long id);

  /**
   * Returns list of cards by given login.
   *
   * @param login user's login
   * @return list of cards
   */
  List<Card> getAllCardsByLogin(String login);

  /**
   * Returns list of cards by given login.
   *
   * @param login    user's login
   * @param pageable pageable
   * @return page of cards
   */
  Page<Card> getAllCardsByLogin(String login, Pageable pageable);

  /**
   * Returns list of cards by current user.
   *
   * @return list of cards
   */
  List<Card> getAllCardsByCurrentUser();

  /**
   * Returns list all non blocked cards of current user.
   * @return list of cards
   */
  List<Card> getAllNonBlockedCardsOfCurrentUser();

  /**
   * Returns card by given id.
   *
   * @param id card id
   * @return card
   */
  Card getCardById(long id);

  /**
   * Saves in the database account entity converted from accountDto.
   *
   * @param card card dto passed by controller
   * @return card entity
   */
  Card createCard(CardDto card);

  /**
   * Setting card active or inactive by id.
   *
   * @param id       card
   * @param isActive boolean
   * @return card entity
   */
  Card setCardActive(long id, boolean isActive);

  /**
   * Returns card by given number card.
   *
   * @param cardNumber String.
   * @return card entity
   */
  Card getCardByCardNumber(String cardNumber);
}
