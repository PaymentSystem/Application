package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.CardArgumentException;
import com.epam.lab.paymentsystem.repository.CardRepository;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.CardService;
import com.epam.lab.paymentsystem.service.UserService;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
   * Returns page of cards by given account id.
   *
   * @param id account id
   * @param pageable pageable
   * @return page of cards
   */
  public Page<Card> getAllCardsByAccountId(long id, Pageable pageable) {
    Account account = accountService.getAccountById(id);
    return cardRepository.getAllByAccount(account, pageable);
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
   * Returns list of cards by given login.
   *
   * @param login user's login
   * @param pageable pageable
   * @return page of cards
   */
  @Override
  public Page<Card> getAllCardsByLogin(String login, Pageable pageable) {
    User user = userService.getUserByLogin(login);
    return cardRepository.getAllByUser(user, pageable);
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

  /**
   * Returns all non blocked cards of current user.
   * @return list of cards
   */
  @Override
  public List<Card> getAllNonBlockedCardsOfCurrentUser() {
    String userLogin = userService.getCurrentUserLogin();
    User currentUser = userService.getUserByLogin(userLogin);
    List<Card> nonBlockedCards = cardRepository.getAllByIsActiveAndUser(true, currentUser);
    List<Card> nonBlockedCardsAndAccounts = nonBlockedCards.stream()
        .filter(card -> card.getAccount().getIsActive())
        .collect(Collectors.toList());
    return nonBlockedCardsAndAccounts;
  }

  /**
   * Creates new card in the database.
   *
   * @param card card dto passed by controller
   * @return card entity
   * @throws CardArgumentException if such user doesn't exists
   */
  @Override
  public Card createCard(CardDto card) throws CardArgumentException {
    LOGGER.info("Creating new card");
    Account account = accountService.getAccountById(card.getAccountId());
    User user = userService.getUserByLogin(card.getUserLogin());
    if (user == null) {
      LOGGER.error("Such user doesn't exists: " + card.getUserLogin());
      throw new CardArgumentException("exception.noSuchUser");
    }
    String cardNumber = generateCardNumber();
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
    Card cardWithNumber = cardRepository.getByCardNumber(number);
    if (cardWithNumber != null) {
      generateCardNumber();
    }
    LOGGER.info("Number card created");
    return number;
  }

  /**
   * Setting card active or inactive by id.
   *
   * @param id card
   * @param isActive boolean
   * @return card entity
   */
  @Override
  public Card setCardActive(long id, boolean isActive) {
    Card card = getCardById(id);
    card.setIsActive(isActive);
    return cardRepository.save(card);
  }
}