package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.repository.OperationRepository;
import com.epam.lab.paymentsystem.service.OperationService;
import com.epam.lab.paymentsystem.service.UserService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class provides implementation of the OperationService, provides methods to manipulate
 * the data using repositories methods. The class is designed to implement business logic.
 */
@Service
public class OperationServiceImpl implements OperationService {
  private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);

  @Autowired
  private OperationRepository operationRepository;

  @Autowired
  private AccountServiceImpl accountService;

  @Autowired
  private CardServiceImpl cardService;

  @Autowired
  private UserService userService;

  /**
   * Make payment operation.
   *
   * @param operation operation.
   */
  @Override
  public void makePayment(Operation operation) {

    Card sourceCard = cardService.getCardById(operation.getSourceCard().getId());
    Card destinationCard = cardService.getCardById(operation.getTargetCard().getId());
    Account srcAccount = sourceCard.getAccount();
    Account dstAccount = destinationCard.getAccount();
    LOGGER.info("Payment operation is successful");
    accountService.makeTransaction(srcAccount, dstAccount, operation.getAmount());
  }

  /**
   * Write history in database.
   *
   * @param operation operation.
   */
  @Override
  public void writeHistory(Operation operation) {
    operationRepository.save(operation);
  }

  /**
   * History operation.
   *
   * @return operation list.
   */
  @Override
  public List<Operation> historyOperation() {
    String userLogin = userService.getCurrentUserLogin();
    List<Card> card = cardService.getAllCardsByLogin(userLogin);
    List<Operation> history = operationRepository.getAllBySourceCardIsIn(card);
    LOGGER.info("Display history operation");
    return history;
  }

  /**
   * History operation by account.
   * * @param accountId long.
   *
   * @return operation list
   */
  @Override
  public List<Operation> historyAccountOperation(long accountId) {
    List<Card> cards = cardService.getAllCardsByAccountId(accountId);
    List<Operation> historyByAccount = operationRepository.getAllBySourceCardIsIn(cards);
    LOGGER.info("Display history operation");
    return historyByAccount;
  }

  /**
   * History operation by card.
   *
   * @param cardId long.
   * @return operation list.
   */
  @Override
  public List<Operation> historyCardOperation(long cardId) {
    List<Operation> historyByCard = operationRepository.getAllBySourceCardId(cardId);
    LOGGER.info("Display history operation");
    return historyByCard;
  }
}
