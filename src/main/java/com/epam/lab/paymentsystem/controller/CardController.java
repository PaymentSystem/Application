package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.service.CardService;
import com.epam.lab.paymentsystem.service.UserService;
import java.util.List;
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
 * Controller for Card,
 * processes the specified URL and redirects
 * the request to the specified page.
 *
 * @author unascribed
 * @since 0.0.1
 */
@Controller
public class CardController {
  private static final Logger LOGGER = LogManager.getLogger(CardController.class);
  private static final String ADD_CARD_PAGE = "addCard";
  private static final String ACCOUNT_PAGE = "account";
  private static final String REDIRECT_TO = "redirect:";

  @Autowired
  private UserService userService;

  @Autowired
  private CardService cardService;

  /**
   * Returns account page with list of all cards linked to that account.
   *
   * @param id    id of account
   * @param model model
   * @return account page view
   */
  @GetMapping(value = "/{userLogin}/account/{accountId}")
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
  @GetMapping(value = "/{userLogin}/account/{accountId}/addCard")
  public String getAddCardPage(Model model) {
    LOGGER.info("Access to card creation page");
    model.addAttribute("userList", userService.getAllUsers());
    model.addAttribute("cardDto", new CardDto());
    return ADD_CARD_PAGE;
  }

  /**
   * Add card form and send it to service layer.
   *
   * @param accountId account
   * @param cardDto   card dto from form
   * @param model     model
   * @return JSP view
   */
  @PostMapping(value = "/{userLogin}/account/{accountId}/addCard")
  public String addCard(
      @PathVariable(name = "accountId") long accountId,
      @ModelAttribute(value = "cardDto") CardDto cardDto,
      Model model) {

    cardDto.setAccountId(accountId);
    try {
      LOGGER.info("Creating new card from web form");
      cardService.createCard(cardDto);
    } catch (UnsupportedOperationException e) {
      model.addAttribute("messageCard", e.getMessage());
      LOGGER.error("Failed to create new card", e);
      return ADD_CARD_PAGE;
    }

    return REDIRECT_TO + "/{userLogin}/account/{accountId}";
  }
}
