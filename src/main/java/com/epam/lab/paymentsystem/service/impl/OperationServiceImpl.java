package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.dto.OperationDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.repository.OperationRepository;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.CardService;
import com.epam.lab.paymentsystem.service.OperationService;
import com.epam.lab.paymentsystem.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  private AccountService accountService;

  @Autowired
  private CardService cardService;

  @Autowired
  private UserService userService;

  /**
   * Make payment operation.
   *
   * @param operationDto operation dto.
   */
  @Override
  @Transactional(rollbackOn = {Exception.class})
  public void makePayment(OperationDto operationDto) {
    Card srcCard = cardService.getCardById(operationDto.getCardSrcId());
    Card dstCard = cardService.getCardById(operationDto.getCardDstId());
    Account srcAccount = srcCard.getAccount();
    Account dstAccount = dstCard.getAccount();

    Operation operation = new Operation(srcCard, dstCard,
            operationDto.getAmount(), LocalDateTime.now().withNano(0));

    accountService.makeTransaction(srcAccount, dstAccount, operation.getAmount());
    LOGGER.info("Payment operation is successful");
    writeHistory(operation);
    LOGGER.info("Payment operation has been written");
  }

  /**
   * Write history in database.
   *
   * @param operation operation.
   */
  protected Operation writeHistory(Operation operation) {
    operationRepository.save(operation);
    return operation;
  }

  /**
   * History operation.
   *
   * @param pageable pageable
   * @return operation page.
   */
  @Override
  public Page<Operation> getAllOperations(Pageable pageable) {
    String userLogin = userService.getCurrentUserLogin();
    List<Card> cards = cardService.getAllCardsByLogin(userLogin);
    Page<Operation> history = operationRepository
            .getAllBySourceCardIsInOrTargetCardIsIn(cards, cards, pageable);
    LOGGER.info("Display history operation");
    return history;
  }

  /**
   * History operation by account.
   *
   * @param accountId long.
   * @param pageable pageable
   * @return operation page
   */
  public Page<Operation> getAllOperationsByAccount(long accountId, Pageable pageable) {
    List<Card> cards = cardService.getAllCardsByAccountId(accountId);
    Page<Operation> historyByAccount = operationRepository
            .getAllBySourceCardIsInOrTargetCardIsIn(cards, cards, pageable);
    LOGGER.info("Display history operation");
    return historyByAccount;
  }

  /**
   * History operation by card.
   *
   * @param cardId long.
   * @param pageable pageable
   * @return operation page.
   */
  @Override
  public Page<Operation> getAllOperationsByCard(long cardId, Pageable pageable) {
    Page<Operation> historyByCard = operationRepository
            .getAllBySourceCardIdOrTargetCardId(cardId, cardId, pageable);
    LOGGER.info("Display history operation");
    return historyByCard;
  }
}
