package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.repository.OperationRepository;
import com.epam.lab.paymentsystem.service.OperationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements OperationService {

  @Autowired
  private OperationRepository operationRepository;

  @Autowired
  private AccountServiceImpl accountService;

  @Autowired
  private CardServiceImpl cardService;

  @Override
  public void makePayment(Operation operation) {

    Card sourceCard = cardService.getCardById(operation.getSourceCard().getId());
    Card destinationCard = cardService.getCardById(operation.getTargetCard().getId());
    Account srcAccount = sourceCard.getAccount();
    Account dstAccount = destinationCard.getAccount();

    accountService.makeTransaction(srcAccount, dstAccount, operation.getAmount());
  }

  @Override
  public void writeHistory(Operation operation) {
    operationRepository.save(operation);
  }

  @Override
  public List<Operation> historyOperation(long cardId) {

    List<Operation> history = operationRepository.getAllBySourceCardId(cardId);
    return history;
  }


}
