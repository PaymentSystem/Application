package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.repository.AccountRepository;
import com.epam.lab.paymentsystem.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

  private long amount;
  private Account account;
  private Account accountTarget;
  @Mock
  private AccountRepository accountRepository;
  @InjectMocks
  private AccountServiceImpl accountService;

  @BeforeEach
  public void startUp() {
    User user = new User();
    account = new Account();
    account.setActive(true);
    account.setUser(user);
    account.setLabel("test");
    accountTarget = new Account();
    accountTarget.setActive(true);
    accountTarget.setUser(user);
    accountTarget.setLabel("test");
    amount = 1000;
  }

  @Test
  public void testCreateAccountThrowsException() {
    account.setAmount(-1);
    assertThrows(UnsupportedOperationException.class,
        () -> accountService.createAccount(account),
        "Amount should be positive");
  }

  @Test
  public void testCreateAccountSavesAccount() {
    account.setAmount(1);
    when(accountRepository.save(account)).thenReturn(account);
    assertEquals(account, accountService.createAccount(account));
  }

  @Test
  public void testMakeTransactionFailsOnIdenticalAccounts() {
    account.setId(1);
    accountTarget.setId(1);
    assertThrows(UnsupportedOperationException.class,
        () -> accountService.makeTransaction(account, accountTarget, amount),
        "Accounts should be different");
  }

  @Test
  public void testMakeTransactionFailsOnSourceAccountAmount() {
    account.setAmount(100);
    assertThrows(UnsupportedOperationException.class,
        () -> accountService.makeTransaction(account, accountTarget, amount),
        "Not enough money");
  }
}
