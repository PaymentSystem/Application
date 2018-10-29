package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

  private static final String ACCOUNT_PAGE = "account";
  private static final String ADD_ACCOUNT_PAGE = "addAccount";

  private AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping(value = "/user/account")
  public String getAccountPage() {
    return ACCOUNT_PAGE;
  }

  @GetMapping(value = "/addAccount")
  public String getAddAccountPage() {
    return ADD_ACCOUNT_PAGE;
  }

  /**
   * Add account form and send it to service layer.
   *
   * @param accountAmount double amount
   * @return JSP view
   */
  @PostMapping(value = "/addAccount")
  public String addAccount(@RequestParam(name = "amount") double accountAmount) {

    Account account = new Account();
    account.setAmount(accountAmount);

    accountService.createAccount(account);
    return ADD_ACCOUNT_PAGE;
  }
}
