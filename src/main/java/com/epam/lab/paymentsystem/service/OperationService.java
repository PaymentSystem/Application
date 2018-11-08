package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.Operation;
import java.util.List;

/**
 * OperationService is the interface that specifies the service methods.
 */
public interface OperationService {

  /**
   * History operation.
   *
   * @return operation list.
   */
  List<Operation> historyOperation();

  /**
   * History operation by account.
   * * @param accountId long.
   *
   * @return operation list
   */
  List<Operation> historyAccountOperation(long accountId);

  /**
   * History operation by card.
   *
   * @param cardId long.
   * @return operation list.
   */
  List<Operation> historyCardOperation(long cardId);

  /**
   * Make payment operation.
   *
   * @param operation operation.
   */
  void makePayment(Operation operation);

  /**
   * Write history in database.
   *
   * @param operation operation.
   */
  void writeHistory(Operation operation);
}

