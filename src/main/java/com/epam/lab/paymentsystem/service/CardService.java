package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import java.util.List;

/**
 * CardService is the interface that specifies the service methods.
 */
public interface CardService {

  List<Card> getAllCards();

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

  List<Card> getAllCardsByCurrentUser();

  List<Card> getAllCardsByAccountIsIn(List<Account> account);

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
   * @param id card
   * @param isActive boolean
   * @return card entity
   */
  Card setCardActive(long id, boolean isActive);
}
