package com.epam.lab.paymentsystem.dao.impl;

import com.epam.lab.paymentsystem.dao.AccountDao;
import com.epam.lab.paymentsystem.entities.Account;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao {
  @Override
  public Account createAccount(Account account) {
    return null;
  }

  @Override
  public List<Account> getAllAccounts() {
    return null;
  }

  @Override
  public Account getAccountById(long id) {
    return null;
  }
}
