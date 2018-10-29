package com.epam.lab.paymentsystem.dao;

import com.epam.lab.paymentsystem.entities.Account;

import java.util.List;

public interface AccountDao {
  Account createAccount(Account account);

  List<Account> getAllAccounts();

  Account getAccountById(long id);
}
