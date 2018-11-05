package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.service.OperationService;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OperationController {

  private static final Logger LOGGER = LogManager.getLogger(com.epam.lab.paymentsystem.controller.OperationController.class);
  private static final String OPERATION_PAGE = "operation";
  private static final String OPERATION_FAIL_PAGE = "operationFail";
  private static final String HISTORY_PAGE = "history";

  /**
   * Instance.
   */
  @Autowired
  private OperationService operationService;


  @GetMapping(value = "/operation")
  public String getOperationPage() {
    return OPERATION_PAGE;
  }
//
//  @GetMapping(value = "/history")
//  public String getHistoryPage() {
//    return HISTORY_PAGE;
//  }

  /**
   * @return view
   */

  @PostMapping(value = "/makePayment")
  @Transactional(rollbackOn = Exception.class)
  public String paymentOperation(@RequestParam(name = "src") Long srcId,
                                 @RequestParam(name = "dst") Long dstId,
                                 @RequestParam(name = "amount") Long amount) {

    LocalDateTime date = LocalDateTime.now();
    Operation operation = new Operation();

    Card srcCard = new Card();
    srcCard.setId(srcId);

    Card dstCard = new Card();
    dstCard.setId(dstId);

    operation.setAmount(amount);
    operation.setSourceCard(srcCard);
    operation.setTargetCard(dstCard);
    operation.setDate(date);

    try {
      operationService.makePayment(operation);
      operationService.writeHistory(operation);

    } catch (UnsupportedOperationException e) {
      LOGGER.error("Exception payment operation", e);
      return OPERATION_FAIL_PAGE;
    }
    return OPERATION_PAGE;
  }

  @PostMapping(value = "/history")
  public String showHistory() {
    return HISTORY_PAGE;
  }

}
