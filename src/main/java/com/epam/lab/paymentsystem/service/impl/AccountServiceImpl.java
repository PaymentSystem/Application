package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dao.AccountDao;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.service.AccountService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

  private AccountDao accountDao;

  public AccountServiceImpl(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public Account createAccount(Account account) {
    Account accountToCreate = new Account();
    if (account.getAmount() < 0) {
      throw new UnsupportedOperationException("Amount should be positive");
    }
    accountToCreate.setAmount(account.getAmount());
    accountToCreate.setActive(true);
    return accountToCreate;
  }

  @Override
  public List<Account> getAllAccounts() {
    return accountDao.getAllAccounts();
  }

  /**
   * Generate list of active accounts.
   *
   * @param isActive boolean flag
   * @return List of accounts
   */
  public List<Account> getAllAccounts(boolean isActive) {
    List<Account> accounts = new ArrayList<>();
    for (Account account : accountDao.getAllAccounts()) {
      if (account.isActive() == isActive) {
        accounts.add(account);
      }
    }
    return accounts;
  }
}
