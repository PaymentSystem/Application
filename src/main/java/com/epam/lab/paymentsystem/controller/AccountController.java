package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

  @Autowired
  private AccountService accountService;

  public AccountController(AccountServiceImpl accountService) {
    this.accountService = accountService;
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

  /**
   * Returns account creation page.
   *
   * @param model model
   * @return HTML view
   */
  @GetMapping(value = "/{userLogin}/addAccount")
  public String getAddAccountPage(Model model) {
    LOGGER.info("Access to account creation page");
    model.addAttribute("accountDto", new AccountDto());
    return ADD_ACCOUNT_PAGE;
  }

  /**
   * Add account form and send it to service layer.
   *
   * @param accountDto accountDto
   * @param model      model
   * @return JSP view
   */
  @PostMapping(value = "/{userLogin}/addAccount")
  public String addAccount(@ModelAttribute(name = "accountDto") AccountDto accountDto,
                           Model model) {

    LOGGER.info("Creating new account from web form");

    try {
      LOGGER.info("Account has been created");
      accountService.createAccount(accountDto);
    } catch (UnsupportedOperationException e) {
      model.addAttribute("messageAccount", e.getMessage());
      LOGGER.error("Failed to create new account", e);
      return ADD_ACCOUNT_PAGE;
    }
    return REDIRECT_TO + "/{userLogin}";
  }
}
