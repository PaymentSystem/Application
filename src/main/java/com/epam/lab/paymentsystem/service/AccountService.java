package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.entities.Account;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * AccountService is the interface that specifies the service methods.
 */
public interface AccountService {

  /**
   * Returns list of accounts by user's login.
   *
   * @param login user's login
   * @param pageable pageable
   * @return list of accounts
   */
  Page<Account> getAllAccountsOfUser(String login, Pageable pageable);

  List<Account> getAllAccountsOfUser(String login);

  /**
   * Saves in the database account entity converted from accountDto.
   *
   * @param account account dto passed by controller
   * @return account entity
   */
  Account createAccount(AccountDto account);

  /**
   * Provides creation of the money transfer transaction.
   *
   * @param source source account
   * @param target target account
   * @param amount amount of transaction
   * @throws UnsupportedOperationException if there's not enough money on source account
   *                                       or was passed identical accounts
   */
  void makeTransaction(Account source, Account target, long amount)
      throws UnsupportedOperationException;

  /**
   * Interface method which provides money transfer.
   *
   * @param source source account
   * @param target target account
   * @param amount amount of transfer
   */
  void moneyTransfer(Account source, Account target, long amount);

  /**
   * Returns account by given id.
   *
   * @param id id
   * @return account entity
   */
  Account getAccountById(long id);

  Account blockAccountById(long id);

  Account unblockAccountById(long id);
}
