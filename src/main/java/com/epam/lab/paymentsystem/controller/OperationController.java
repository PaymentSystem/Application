package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.OperationDto;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.service.CardService;
import com.epam.lab.paymentsystem.service.OperationService;
import com.epam.lab.paymentsystem.utility.DateConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

  @Autowired
  private OperationService operationService;

  @Autowired
  private CardService cardService;

  /**
   * Get method for operation page.
   *
   * @param model model
   * @return String
   */
  @GetMapping(value = "/{userLogin}/operation")
  public String getOperationPage(Model model) {
    model.addAttribute("srcNumberCardList", cardService.getAllNonBlockedCardsOfCurrentUser());
    model.addAttribute("operationDto", new OperationDto());
    return OPERATION_PAGE;
  }

  /**
   * Payment operation .
   *
   * @param operationDto operation dto
   * @param model        Model.
   * @return String
   */
  @PostMapping(value = "/{userLogin}/operation")
  public String paymentOperation(
      @ModelAttribute(value = "operationDto") OperationDto operationDto,
      Model model) {
    operationService.makePayment(operationDto);
    LOGGER.info("Payment operation is successful");
    model.addAttribute("message", "Money transfer successful!");
    return OPERATION_PAGE;
  }

  /**
   * Get history on different pages.
   *
   * @param model Model
   * @return String
   */
  @GetMapping(value = "/{userLogin}/history")
  public String getUserHistory(
      @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable,
      Model model) {
    Page<Operation> history = operationService.getAllOperations(pageable);
    model.addAttribute("historyOperationPage", history);
    LOGGER.info("Access to history creation page");
    return HISTORY_PAGE;
  }

  /**
   * Get account history.
   *
   * @param accountId long
   * @param model     Model
   * @return string
   */
  @GetMapping(value = "/{userLogin}/account/{accountId}/history")
  public String getAccountHistory(
      @PathVariable(name = "accountId") long accountId,
      @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable,
      Model model) {
    Page<Operation> operations = operationService.getAllOperationsByAccount(accountId, pageable);
    Page<Operation> history = DateConverter.dateConverter(operations);
    model.addAttribute("historyOperationPage", history);
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
  @GetMapping(value = "/{userLogin}/account/{accountId}/card/{cardId}/history")
  public String getCardHistory(
      @PathVariable(name = "cardId") long cardId,
      @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable,
      Model model) {
    Page<Operation> operations = operationService.getAllOperationsByCard(cardId, pageable);
    Page<Operation> history = DateConverter.dateConverter(operations);
    model.addAttribute("historyOperationPage", history);
    LOGGER.info("Access to history creation page");
    return HISTORY_PAGE;
  }
}