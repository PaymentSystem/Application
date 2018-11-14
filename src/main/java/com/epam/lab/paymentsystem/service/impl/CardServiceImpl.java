package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.repository.CardRepository;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.CardService;
import com.epam.lab.paymentsystem.service.UserService;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class provides implementation of the CardService, provides methods to manipulate
 * the data using repositories methods. The class is designed to implement business logic.
 */
@Service
public class CardServiceImpl implements CardService {

  private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);
  private static Random random = new Random();

  @Autowired
  private CardRepository cardRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private AccountService accountService;

  /**
   * Returns list of all cards.
   *
   * @return list of cards
   */
  @Override
  public List<Card> getAllCards() {
    return cardRepository.findAll();
  }

  /**
   * Returns list of cards by given account id.
   *
   * @param id account id
   * @return list of cards
   */
  @Override
  public List<Card> getAllCardsByAccountId(long id) {
    Account account = accountService.getAccountById(id);
    return cardRepository.getAllByAccount(account);
  }

  /**
   * Returns list of cards by given login.
   *
   * @param login user's login
   * @return list of cards
   */
  @Override
  public List<Card> getAllCardsByLogin(String login) {
    User user = userService.getUserByLogin(login);
    return cardRepository.getAllByUser(user);
  }

  /**
   * Returns list of cards by current user.
   *
   * @return list of cards
   */
  @Override
  public List<Card> getAllCardsByCurrentUser() {
    return getAllCardsByLogin(userService.getCurrentUserLogin());
  }

  @Override
  public List<Card> getAllCardsByCurrentUserWithoutBlocked() {
    String userLogin = userService.getCurrentUserLogin();
    User currentUser = userService.getUserByLogin(userLogin);
    return cardRepository.getAllByUserOrIsActive(currentUser, true);
  }

  /**
   * Creates new card in the database.
   *
   * @param card card dto passed by controller
   * @return card entity
   * @throws UnsupportedOperationException if such user doesn't exists
   */
  @Override
  public Card createCard(CardDto card) throws UnsupportedOperationException {
    LOGGER.info("Creating new card");
    Account account = accountService.getAccountById(card.getAccountId());
    User user = userService.getUserByLogin(card.getUserLogin());
    String cardNumber = generateCardNumber();
    if (user == null) {
      LOGGER.error("Such user doesn't exists: " + card.getUserLogin());
      throw new UnsupportedOperationException("No such user");
    }

    Card cardToCreate = new Card(account, user, card.getLabel(), true, cardNumber);
    LOGGER.info("Card has been created");
    return cardRepository.save(cardToCreate);
  }

  /**
   * Returns card by given number card.
   *
   * @param cardNumber String.
   * @return card entity
   */
  @Override
  public Card getCardByCardNumber(String cardNumber) {
    return cardRepository.getCardByCardNumber(cardNumber);
  }

  /**
   * Returns card by given id.
   *
   * @param id card id
   * @return card
   */
  @Override
  public Card getCardById(long id) {
    if (cardRepository.getCardById(id) == null) {
      LOGGER.error("Card not exist");
      throw new UnsupportedOperationException("Card not exist");
    }
    return cardRepository.getCardById(id);
  }

  /**
   * Return unique number card.
   *
   * @return string number card
   */
  private String generateCardNumber() {
    String number = String.format("%04d", random.nextInt(10000));
    Card cardWithNumber = cardRepository.findByCardNumber(number);
    if (cardWithNumber != null) {
      LOGGER.info("Number card already exist");
      generateCardNumber();
    }
    return number;
  }

}