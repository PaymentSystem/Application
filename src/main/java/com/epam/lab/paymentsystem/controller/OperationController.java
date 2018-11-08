package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.service.OperationService;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
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
  private static final String OPERATION_PAGE = "operation";
  private static final String HISTORY_PAGE = "history";
  private static long cardSrcId;
  private static long cardDstId;
  private static LocalDateTime date;

  @Autowired
  private OperationService operationService;


  @GetMapping(value = "/operation")
  public String getOperationPage() {
    return OPERATION_PAGE;
  }

  /**
   * Payment operation .
   *
   * @param srcId  Long, source card.
   * @param dstId  Long, destination card.
   * @param amount Long, amount money.
   * @param model  Model.
   * @return String
   */
  @PostMapping(value = "/makePayment")
  @Transactional(rollbackOn = Exception.class)
  public String paymentOperation(@RequestParam(name = "src") Long srcId,
                                 @RequestParam(name = "dst") Long dstId,
                                 @RequestParam(name = "amount") Long amount,
                                 Model model) {
    cardSrcId = srcId;
    cardDstId = dstId;
    date = LocalDateTime.now();
    Operation operation = new Operation();

    Card srcCard = new Card();
    srcCard.setId(cardSrcId);

    Card dstCard = new Card();
    dstCard.setId(cardDstId);

    operation.setAmount(amount);
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
    return OPERATION_PAGE;
  }

  /**
   * Get history on different pages.
   *
   * @param model Model
   * @return String
   */
  @GetMapping(value = "/history")
  public String getUserHistory(Model model) {

    List<Operation> history = operationService.historyOperation();
    model.addAttribute("historyOperation", history);
    LOGGER.info("Access to history creation page");
    return HISTORY_PAGE;
  }

  /** Get accaunt history.
   * @param accountId long
   * @param model Vodel
   * @return string
   */
  @GetMapping(value = "/user/history/{accountId}")
  public String getAccountHistory(@PathVariable(name = "accountId") long accountId, Model model) {

    List<Operation> history = operationService.historyAccountOperation(accountId);
    model.addAttribute("historyOperation", history);
    LOGGER.info("Access to history creation page");
    return HISTORY_PAGE;
  }

  /** Get card history.
   * @param cardId long
   * @param model Model
   * @return String
   */
  @GetMapping(value = "/user/account/{accountId}/history/{cardId}")
  public String getCardHistory(@PathVariable(name = "cardId") long cardId, Model model) {

    List<Operation> history = operationService.historyCardOperation(cardId);
    model.addAttribute("historyOperation", history);
    LOGGER.info("Access to history creation page");
    return HISTORY_PAGE;
  }

}