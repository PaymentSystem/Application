package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.service.CardService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
  @Override
  public List<Card> getAllCardsByAccount(Account account) {
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

  @Override
  public Account getAccountByCard(Card card) {
    return null;
  }
}
