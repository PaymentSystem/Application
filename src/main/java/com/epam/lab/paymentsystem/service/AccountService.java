package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.Account;

import java.util.List;

public interface AccountService {

  List<Account> getAllAccountsByLogin(String login);

  Account createAccount(Account account);

  void makeTransaction(Account source, Account target);
}
