package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.CardService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CardController {
  private static final Logger LOGGER = LogManager.getLogger(AccountController.class);
  private static final String ACCOUNT_PAGE = "account";
  @Autowired
  private CardService cardService;
  @Autowired
  private AccountService accountService;

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
    List<Card> cards = cardService.getAllCardsByAccountId(id);
    model.addAttribute("cardList", cards);
    return ACCOUNT_PAGE;
  }
}
