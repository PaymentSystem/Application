package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.CardService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
  @Autowired
  private AccountService accountService;
  @Override
  public List<Card> getAllCardsByAccountId(long id) {
    Account account = accountService.getAccountById(id);
    return null;
  }

  @Override
  public List<Card> getAllCardsByLogin(String login) {
    return null;
  }

  @Override
  public Card createCard(Card card) {
    return null;
  }
}
