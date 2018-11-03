package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;

import java.util.List;

public interface CardService {

  List<Card> getAllCardsByLogin(String login);

  Card createCard(Card card);

  Account getAccountByCard(Card card);
}
