package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.Card;
import java.util.List;

public interface CardService {

  List<Card> getAllCardsByAccountId(long id);

  List<Card> getAllCardsByLogin(String login);

  Card createCard(Card card);

  Card getCardById(Long id);
}
