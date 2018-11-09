package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Role;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.repository.AccountRepository;
import com.epam.lab.paymentsystem.service.impl.AccountServiceImpl;
import com.epam.lab.paymentsystem.utility.converter.TransformerToDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

  private Role role;
  private User user;
  private AccountDto accountDto;
  private long amount;
  private String login;
  private Account account;
  private Account accountTarget;
  @Mock
  private UserService userService;
  @Mock
  private AccountRepository accountRepository;
  @InjectMocks
  private AccountServiceImpl accountService;

  @BeforeEach
  public void startUp() {
    MockitoAnnotations.initMocks(this);
    role = new Role();
    user = new User();
    account = new Account(user, "test", 0, true);
    accountTarget = new Account(user, "test", 0, true);
    amount = 1000;
    login = "test";
  }

  @Test
  public void testCreateAccountThrowsExceptionWhenAmountIsNegative() {
    account.setAmount(-1);
    accountDto = TransformerToDto.convertAccount(account);
    assertThrows(UnsupportedOperationException.class,
        () -> accountService.createAccount(accountDto),
        "Amount should be positive");
  }

  @Test
  public void testCreateAccountThrowsExceptionWhenUserIsInBlockedStatus() {
    account.setAmount(1);
    role.setRoleStatus(Roles.BLOCKED);
    user.setRole(role);
    accountDto = TransformerToDto.convertAccount(account);
    when(userService.getCurrentUserLogin()).thenReturn(login);
    when(userService.getUserByLogin(login)).thenReturn(user);
    assertThrows(UnsupportedOperationException.class,
        () -> accountService.createAccount(accountDto),
        "User is blocked");
  }

  @Test
  public void testCreateAccountSavesAccount() {
    account.setAmount(1);
    role.setRoleStatus(Roles.USER);
    user.setRole(role);
    accountDto = TransformerToDto.convertAccount(account);
    when(userService.getCurrentUserLogin()).thenReturn(login);
    when(userService.getUserByLogin(login)).thenReturn(user);
    when(accountRepository.save(account)).thenReturn(account);
    assertEquals(account, accountService.createAccount(accountDto));
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

  @Test
  public void testMakeTransactionFailsOnSourceAccountIsBlocked() {
    account.setIsActive(false);
    assertThrows(UnsupportedOperationException.class,
        () -> accountService.makeTransaction(account, accountTarget, amount),
        "Not enough money");
  }

  @Test
  public void testMakeTransactionFailsOnTargetAccountIsBlocked() {
    accountTarget.setIsActive(false);
    assertThrows(UnsupportedOperationException.class,
        () -> accountService.makeTransaction(account, accountTarget, amount),
        "Not enough money");
  }
}
