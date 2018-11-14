package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.OperationDto;
import com.epam.lab.paymentsystem.entities.Operation;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * OperationService is the interface that specifies the service methods.
 */
public interface OperationService {

  /**
   * History operation.
   *
   * @param pageable pageable
   * @return operation page.
   */
  Page<Operation> getAllOperations(Pageable pageable);

  /**
   * History operation by account.
   *
   * @param accountId long.
   * @param pageable pageable
   * @return operation page
   */
  Page<Operation> getAllOperationsByAccount(long accountId, Pageable pageable);

  /**
   * History operation by card.
   *
   * @param cardId long.
   * @param pageable pageable
   * @return operation page.
   */
  Page<Operation> getAllOperationsByCard(long cardId, Pageable pageable);

  /**
   * Make payment operation.
   *
   * @param operationDto operation dto.
   */
  void makePayment(OperationDto operationDto);
}

