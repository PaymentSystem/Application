package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.repository.AccountRepository;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.CardService;
import com.epam.lab.paymentsystem.service.UserService;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class provides implementation of the AccountService, provides methods to manipulate
 * the data using repositories methods. The class is designed to implement business logic.
 */
@Service
public class AccountServiceImpl implements AccountService {

  private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);

  private AccountRepository accountRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private CardService cardService;

  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  /**
   * Creates new account in the database.
   *
   * @param accountDto account
   * @return account entity
   */
  @Override
  public Account createAccount(AccountDto accountDto) throws UnsupportedOperationException {
    LOGGER.info("Creating new account");
    Account accountToCreate = new Account();
    if (accountDto.getAmount() < 0) {
      LOGGER.error("Passed negative amount of account");
      throw new UnsupportedOperationException("Amount should be positive");
    }
    accountToCreate.setAmount(accountDto.getAmount());
    accountToCreate.setLabel(accountDto.getLabel());
    accountToCreate.setUser(accountDto.getUser());
    accountToCreate.setActive(true);
    LOGGER.info("Account has been created");

    return accountRepository.save(accountToCreate);
  }

  /**
   * Describes the process of creating a transaction which allows money transfer.
   *
   * @param source source account
   * @param target target account
   * @param amount amount
   * @throws UnsupportedOperationException if there's not enough money on source account
   */
  @Override
  @Transactional
  public void makeTransaction(Account source, Account target, long amount)
      throws UnsupportedOperationException {
    LOGGER.info("Creating transaction");
    if (source.getAmount() - amount < 0) {
      LOGGER.error("Not enough money on source account");
      throw new UnsupportedOperationException("Not enough money");
    }
    if (source.getId() == target.getId()) {
      LOGGER.error("Passed same accounts");
      throw new UnsupportedOperationException("Accounts should be different");
    }
    source.setAmount(source.getAmount() - amount);
    target.setAmount(target.getAmount() + amount);
    LOGGER.info("Transaction has been created");
    accountRepository.save(source);
    accountRepository.save(target);
  }

  @Override
  public Account getAccountById(long id) {
    return accountRepository.getAccountById(id);
  }

  @Override
  public List<Account> getAllAccountsByLogin(String login) {
    User user = userService.getUserByLogin(login);
    return accountRepository.getAllByUser(user);
  }
}
