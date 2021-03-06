package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.CardService;
import com.epam.lab.paymentsystem.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

  @Autowired
  private AccountService accountService;

  /**
   * Returns account page with list of all cards linked to that account.
   *
   * @param login    user's login
   * @param id       id of account
   * @param pageable pageable
   * @param model    model
   * @return account page view
   */
  @GetMapping(value = "/{userLogin}/account/{accountId}")
  public String getAccountPage(
      @PathVariable(name = "userLogin") String login,
      @PathVariable(name = "accountId") long id,
      @PageableDefault(size = 5, sort = {"label"}) Pageable pageable,
      Model model) {

    LOGGER.info("Access to account page");
    Page<Card> cards = cardService.getAllCardsByAccountId(id, pageable);
    model.addAttribute("cardPage", cards);
    model.addAttribute("currentUserLogin", userService.getCurrentUserLogin());
    Account account = accountService.getAccountById(id);
    long amountCard = account.getAmount();
    model.addAttribute("amountCard", amountCard);
    return ACCOUNT_PAGE;
  }

  /**
   * Returns account page with list of all cards linked to that user.
   *
   * @param login    user's login
   * @param pageable pageable
   * @param model    model
   * @return account page view
   */
  @GetMapping(value = "/{userLogin}/cards")
  public String getAccountPageUserCards(
      @PathVariable(name = "userLogin") String login,
      @PageableDefault(size = 5, sort = {"label"}) Pageable pageable,
      Model model) {

    LOGGER.info("Access to account page");
    Page<Card> cards = cardService.getAllCardsByLogin(login, pageable);
    model.addAttribute("cardPage", cards);
    model.addAttribute("currentUserLogin", userService.getCurrentUserLogin());
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
   * @return JSP view
   */
  @PostMapping(value = "/{userLogin}/account/{accountId}/addCard")
  public String addCard(
      @PathVariable(name = "accountId") long accountId,
      @ModelAttribute(value = "cardDto") CardDto cardDto) {

    cardDto.setAccountId(accountId);
    cardService.createCard(cardDto);
    return REDIRECT_TO + "/{userLogin}/account/{accountId}";
  }

  /**
   * Blocking or activating card.
   *
   * @param id             card id
   * @param isBlock        boolean
   * @param servletRequest servlet request
   * @return redirect to card's account page
   */
  @PostMapping(value = "/{userLogin}/account/{accountId}/card/{cardId}/blocking/{isBlock}")
  public String blockCard(@PathVariable(name = "cardId") long id,
                          @PathVariable(name = "isBlock") boolean isBlock,
                          HttpServletRequest servletRequest) {
    cardService.setCardActive(id, !isBlock);
    String referer = servletRequest.getHeader("Referer");
    return REDIRECT_TO + referer;
  }
}
