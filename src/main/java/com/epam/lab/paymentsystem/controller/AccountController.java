package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.UserService;
import com.epam.lab.paymentsystem.service.impl.AccountServiceImpl;
import com.epam.lab.paymentsystem.service.impl.UserServiceImpl;
import com.epam.lab.paymentsystem.utility.converter.CurrentUser;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for Account,
 * processes the specified URL and redirects
 * the request to the specified page.
 *
 * @author unascribed
 * @since 0.0.1
 */
@Controller
public class AccountController {
  private static final Logger LOGGER = LogManager.getLogger(AccountController.class);
  private static final String ACCOUNT_PAGE = "account";
  private static final String ADD_ACCOUNT_PAGE = "addAccount";
  private static final String REDIRECT_TO = "redirect:";

  @Autowired
  private UserService userService;

  @Autowired
  private AccountService accountService;

  public AccountController(AccountServiceImpl accountService, UserServiceImpl userService) {
    this.accountService = accountService;
    this.userService = userService;
  }

  /**
   * Returns account page with list of all cards linked to that account.
   *
   * @param id    id of account
   * @param model model
   * @return account page view
   */
  @GetMapping(value = "/user/account/{accountId}")
  public String getAccountPage(@PathVariable(name = "accountId") long id, Model model) {
    LOGGER.info("Access to account page");
    Account account = accountService.getAccountById(id);
    List<Card> cards = accountService.getAllCardsByAccount(account);
    model.addAttribute("cardList", cards);
    return ACCOUNT_PAGE;
  }

  @GetMapping(value = "/user/addAccount")
  public String getAddAccountPage() {
    LOGGER.info("Access to account creation page");
    return ADD_ACCOUNT_PAGE;
  }

  /**
   * Add account form and send it to service layer.
   *
   * @param accountAmount double amount
   * @param accountLabel  label
   * @param model         model
   * @return JSP view
   */
  @PostMapping(value = "/user/addAccount")
  public String addAccount(@RequestParam(name = "label") String accountLabel,
                           @RequestParam(name = "amount") long accountAmount,
                           Model model) {

    User user = userService.getUserByLogin(CurrentUser.getCurrentUserLogin());
    AccountDto accountDto = new AccountDto();
    accountDto.setAmount(accountAmount);
    accountDto.setLabel(accountLabel);
    accountDto.setUser(user);
    LOGGER.info("Creating new account from web form");

    try {
      LOGGER.info("Account has been created");
      accountService.createAccount(accountDto);
    } catch (UnsupportedOperationException e) {
      model.addAttribute("messageAccount", e.getMessage());
      LOGGER.error("Failed to create new account", e);
    }
    return REDIRECT_TO;
  }
}
