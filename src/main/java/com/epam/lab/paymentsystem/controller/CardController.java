package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.CardService;

import com.epam.lab.paymentsystem.service.UserService;
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

@Controller
public class CardController {
  @Autowired private UserService userService;
  @Autowired private CardService cardService;
  @Autowired private AccountService accountService;

  private static final Logger LOGGER = LogManager.getLogger(AccountController.class);
  private static final String ADD_CARD_PAGE = "addCard";
  private static final String ACCOUNT_PAGE = "account";
  private static final String REDIRECT_TO = "redirect:";

  /**
   * Returns account page with list of all cards linked to that account.
   *
   * @param id id of account
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

  /**
   * Returns add new card page.
   *
   * @param model model
   * @return card add page view
   */
  @GetMapping(value = "/user/account/{accountId}/addCard")
  public String getAddCardPage(Model model) {
    LOGGER.info("Access to card creation page");
    model.addAttribute("userList", userService.getAllUsers());
    return ADD_CARD_PAGE;
  }

  /**
   * Add card form and send it to service layer.
   *
   * @param accountId account
   * @param cardLabel label
   * @param userLogin login
   * @param model model
   * @return JSP view
   */
  @PostMapping(value = "/user/account/{accountId}/addCard")
  public String addCard(
      @PathVariable(name = "accountId") long accountId,
      @RequestParam(name = "label") String cardLabel,
      @RequestParam(name = "login") String userLogin,
      Model model) {

    CardDto cardDto = new CardDto(0, accountId, userLogin, cardLabel, false);
    LOGGER.info("Creating new card from web form");
    cardService.createCard(cardDto);

    return REDIRECT_TO;
  }
}
