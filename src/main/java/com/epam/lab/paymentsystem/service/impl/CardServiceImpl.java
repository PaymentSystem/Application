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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

  private CardRepository cardRepository;

  private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);

  @Autowired private UserService userService;
  @Autowired private AccountService accountService;

  public CardServiceImpl(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Override
  public List<Card> getAllCardsByAccountId(long id) {
    Account account = accountService.getAccountById(id);
    return cardRepository.getAllByAccount(account);
  }

  @Override
  public List<Card> getAllCardsByLogin(String login) {
    return null;
  }

  @Override
  public Card createCard(CardDto card) {
    LOGGER.info("Creating new card");
    Account account = accountService.getAccountById(card.getAccountId());
    User user = userService.getUserByLogin(card.getUserLogin());
    Card cardToCreate = new Card(account, user, card.getLabel(), true);
    LOGGER.info("Card has been created");
    return cardRepository.save(cardToCreate);
  }
}
