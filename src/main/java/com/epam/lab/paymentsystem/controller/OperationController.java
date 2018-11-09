package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.OperationDto;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.service.CardService;
import com.epam.lab.paymentsystem.service.OperationService;
import com.epam.lab.paymentsystem.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
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
 * Controller for Payment operation,
 * processes the specified URL and redirects
 * the request to the specified page.
 *
 * @author unascribed
 * @since 0.0.1
 */
@Controller
public class OperationController {

  private static final Logger LOGGER = LogManager.getLogger(OperationController.class);
  private static final String HISTORY_PAGE = "history";
  private static final String OPERATION_PAGE = "operation";
  private static final String REDIRECT_TO = "redirect:";

  @Autowired
  private OperationService operationService;

  @Autowired
  private CardService cardService;

  @Autowired
  private UserService userService;

  /**
   * Get method for operation page.
   *
   * @param model model
   * @return String
   */
  @GetMapping(value = "/operation")
  public String getOperationPage(Model model) {
    model.addAttribute("srcCardList", cardService.getAllCardsByCurrentUser());
    model.addAttribute("operationDto", new OperationDto());
    return OPERATION_PAGE;
  }

  /**
   * Payment operation .
   *
   * @param operationDto operation dto
   * @param model  Model.
   * @return String
   */
  @PostMapping(value = "/operation")
  @Transactional(rollbackOn = Exception.class)
  public String paymentOperation(
      @ModelAttribute(value = "operationDto") OperationDto operationDto,
      Model model) {
    LocalDateTime date = LocalDateTime.now().withNano(0);
    Operation operation = new Operation();

    Card srcCard = cardService.getCardById(operationDto.getCardSrcId());
    Card dstCard = cardService.getCardById(operationDto.getCardDstId());

    operation.setAmount(operationDto.getAmount());
    operation.setSourceCard(srcCard);
    operation.setTargetCard(dstCard);
    operation.setDate(date);
    LOGGER.info("Payment operation is successful");

    try {
      operationService.makePayment(operation);
      operationService.writeHistory(operation);
    } catch (UnsupportedOperationException e) {
      model.addAttribute("message", e.getMessage());
      LOGGER.error("Exception payment operation", e);
      return OPERATION_PAGE;
    }
    return REDIRECT_TO + "/" + userService.getCurrentUserLogin();
  }

  /**
   * Get history on different pages.
   *
   * @param model Model
   * @return String
   */
  @GetMapping(value = "/history")
  public String getUserHistory(Model model) {
    List<Operation> history = operationService.getAllOperations();
    model.addAttribute("historyOperation", history);
    LOGGER.info("Access to history creation page");
    return HISTORY_PAGE;
  }

  /**
   * Get accaunt history.
   *
   * @param accountId long
   * @param model     Model
   * @return string
   */
  @GetMapping(value = "/{userLogin}/history/{accountId}")
  public String getAccountHistory(@PathVariable(name = "accountId") long accountId, Model model) {
    List<Operation> history = operationService.getAllOperationsByAccount(accountId);
    model.addAttribute("historyOperation", history);
    LOGGER.info("Access to history creation page");
    return HISTORY_PAGE;
  }

  /**
   * Get card history.
   *
   * @param cardId long
   * @param model  Model
   * @return String
   */
  @GetMapping(value = "/{userLogin}/account/{accountId}/history/{cardId}")
  public String getCardHistory(@PathVariable(name = "cardId") long cardId, Model model) {
    List<Operation> history = operationService.getAllOperationsByCard(cardId);
    model.addAttribute("historyOperation", history);
    LOGGER.info("Access to history creation page");
    return HISTORY_PAGE;
  }
}