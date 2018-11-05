package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import java.util.List;

public interface AccountService {

  List<Account> getAllAccountsByLogin(String login);

  Account createAccount(Account account);

  void makeTransaction(Account source, Account target, long amount)
      throws UnsupportedOperationException;

  Account getAccountById(long id);

  List<Card> getAllCardsByAccount(Account account);
}
