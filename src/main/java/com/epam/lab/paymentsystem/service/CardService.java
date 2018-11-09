package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Card;
import java.util.List;

/**
 * CardService is the interface that specifies the service methods.
 */
public interface CardService {

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
   * Saves in the database account entity converted from accountDto.
   *
   * @param card card dto passed by controller
   * @return card entity
   */
  Card createCard(CardDto card);
}
