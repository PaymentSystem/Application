package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.Account;

import java.util.List;

public interface AccountService {
  Account createAccount(Account account);

  List<Account> getAllAccounts();
}
