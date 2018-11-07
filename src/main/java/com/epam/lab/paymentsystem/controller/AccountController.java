package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.UserService;
import com.epam.lab.paymentsystem.service.impl.AccountServiceImpl;
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
  private static final String ADD_ACCOUNT_PAGE = "addAccount";
  private static final String REDIRECT_TO = "redirect:";
  private static final String USER_PAGE = "user";

  @Autowired
  private UserService userService;

  @Autowired
  private AccountService accountService;

  public AccountController(AccountServiceImpl accountService) {
    this.accountService = accountService;
  }

  /**
   * Returns user page with list of all accounts linked to that user.
   *
   * @param model model
   * @return user page view
   */
  @GetMapping(value = "/{userLogin}")
  public String getUserPage(@PathVariable(name = "userLogin") String login,  Model model) {
    List<Account> accounts = accountService.getAllAccountsOfCurrentUser(login);
    model.addAttribute("accountList", accounts);
    String currentUserLogin = userService.getCurrentUserLogin();
    model.addAttribute("currentUserLogin", currentUserLogin);
    User userOnPage = userService.getUserByLogin(login);
    model.addAttribute("userOnPage", userOnPage);

    return USER_PAGE;
  }

  @PostMapping(value = "/{userLogin}/block")
  public String blockUser(@PathVariable(name = "userLogin") String userLogin) {
    userService.blockUser(userService.getUserByLogin(userLogin));
    return REDIRECT_TO + "/{userLogin}";
  }

  @PostMapping(value = "/{userLogin}/unblock")
  public String unblockUser(@PathVariable(name = "userLogin") String userLogin) {
    userService.unblockUser(userService.getUserByLogin(userLogin));
    return REDIRECT_TO + "/{userLogin}";
  }

  @PostMapping(value = "/{userLogin}/account/{accountId}/block")
  public String blockAccount(@PathVariable(name = "accountId") long id) {
    accountService.blockAccountById(id);
    return REDIRECT_TO + "/{userLogin}";
  }

  @PostMapping(value = "/{userLogin}/account/{accountId}/unblock")
  public String unblockAccount(@PathVariable(name = "accountId") long id) {
    accountService.unblockAccountById(id);
    return REDIRECT_TO + "/{userLogin}";
  }

  @GetMapping(value = "/{userLogin}/addAccount")
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
  @PostMapping(value = "/{userLogin}/addAccount")
  public String addAccount(@RequestParam(name = "label") String accountLabel,
                           @RequestParam(name = "amount") long accountAmount,
                           Model model) {

    AccountDto accountDto = new AccountDto(0, null, accountLabel, accountAmount, false);
    LOGGER.info("Creating new account from web form");

    try {
      LOGGER.info("Account has been created");
      accountService.createAccount(accountDto);
    } catch (UnsupportedOperationException e) {
      model.addAttribute("messageAccount", e.getMessage());
      LOGGER.error("Failed to create new account", e);
    }
    return REDIRECT_TO + "/{userLogin}";
  }
}
