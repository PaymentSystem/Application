package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.AccountArgumentException;
import com.epam.lab.paymentsystem.exception.MoneyTransferException;
import com.epam.lab.paymentsystem.repository.AccountRepository;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.UserService;
import com.epam.lab.paymentsystem.utility.converter.TransformerToEntity;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * This class provides implementation of the AccountService, provides methods to manipulate
 * the data using repositories methods. The class is designed to implement business logic.
 */
@Service
public class AccountServiceImpl implements AccountService {

  private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private UserService userService;

  /**
   * Creates new account in the database.
   *
   * @param accountDto account
   * @return account entity
   */
  @Override
  public Account createAccount(AccountDto accountDto) throws AccountArgumentException {
    LOGGER.info("Creating new account");
    if (accountDto.getAmount() < 0) {
      LOGGER.error("Passed negative amount of account");
      throw new AccountArgumentException("exception.amount");
    }
    Account accountToCreate = TransformerToEntity.convertAccount(accountDto);

    String login = userService.getCurrentUserLogin();
    User user = userService.getUserByLogin(login);
    if (!user.roleStatusEquals("USER")) {
      throw new AccountArgumentException("exception.user.blocked");
    }
    accountToCreate.setUser(user);
    accountToCreate.setIsActive(true);
    LOGGER.info("Account has been created");

    return accountRepository.save(accountToCreate);
  }

  /**
   * Describes the process of creating a transaction which allows money transfer.
   *
   * @param source source account
   * @param target target account
   * @param amount amount
   * @throws MoneyTransferException if there's not enough money on source account
   */
  @Override
  @Transactional
  public void makeTransaction(Account source, Account target, long amount)
      throws MoneyTransferException {
    if (source.getId() == target.getId()) {
      LOGGER.error("Passed same accounts");
      throw new MoneyTransferException("exception.account.different");
    }
    if ((source.getAmount() - amount) < 0) {
      LOGGER.error("Not enough money on source account");
      throw new MoneyTransferException("exception.account.money");
    }
    if ((amount <= 0)) {
      LOGGER.error("Not correct amount");
      throw new MoneyTransferException("exception.amount");
    }
    if (!source.getIsActive()) {
      LOGGER.error("Source account is blocked");
      throw new MoneyTransferException("exception.account.source");
    }
    if (!target.getIsActive()) {
      LOGGER.error("Target account is blocked");
      throw new MoneyTransferException("exception.account.target");
    }
    LOGGER.info("Creating transaction");
    moneyTransfer(source, target, amount);
    LOGGER.info("Transaction has been created");
  }

  /**
   * Provides money transfer.
   *
   * @param source source account
   * @param target target account
   * @param amount amount of transfer
   */
  public void moneyTransfer(Account source, Account target, long amount) {
    source.setAmount(source.getAmount() - amount);
    target.setAmount(target.getAmount() + amount);
    accountRepository.save(source);
    accountRepository.save(target);
  }

  /**
   * Returns account by given id.
   *
   * @param id id
   * @return account entity
   */
  @Override
  public Account getAccountById(long id) {
    return accountRepository.getAccountById(id);
  }

  /**
   * Block account by given id.
   *
   * @param id long
   * @return account entity
   */
  @Override
  public Account blockAccountById(long id) {
    Account account = accountRepository.getAccountById(id);
    account.setIsActive(false);
    return accountRepository.save(account);
  }

  /**
   * Unblock account by given id.
   *
   * @param id long
   * @return account entity
   */
  @Override
  public Account unblockAccountById(long id) {
    Account account = accountRepository.getAccountById(id);
    account.setIsActive(true);
    return accountRepository.save(account);
  }

  /**
   * Add amount in account.
   *
   * @param accountId long
   * @param amount    long
   * @return account entity
   */
  @Override
  public Account addAmount(long accountId, long amount)
      throws AccountArgumentException {

    Account account = accountRepository.getAccountById(accountId);
    if (amount <= 0) {
      LOGGER.error("Amount should be positive");
      throw new AccountArgumentException("exception.amount");
    }
    account.setAmount(account.getAmount() + amount);

    LOGGER.info("Account's amount has been updated");
    return accountRepository.save(account);
  }

  /**
   * Returns list of accounts by user's login.
   *
   * @param login user's login
   * @param pageable number of page
   * @return list of accounts
   */
  @Override
  public Page<Account> getAllAccountsOfUser(String login, Pageable pageable) {
    User user = userService.getUserByLogin(login);
    return accountRepository.getAllByUser(user, pageable);
  }

  /**
   * Returns list of accounts by user's login.
   *
   * @param login user's login
   * @return list of accounts
   */
  @Override
  public List<Account> getAllAccountsOfUser(String login) {
    User user = userService.getUserByLogin(login);
    return accountRepository.getAllByUser(user);
  }
}
