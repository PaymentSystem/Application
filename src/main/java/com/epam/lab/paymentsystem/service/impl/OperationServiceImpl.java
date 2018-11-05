package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.repository.OperationRepository;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements OperationService {

  @Autowired
  private AccountService accountService;


  @Autowired
  private OperationRepository operationRepository;


  @Override
  public void makePayment(Operation operation) {

    Account srcAccount = operationRepository.getAccountByCard(operation.getSourceCard());
    Account dstAccount = operationRepository.getAccountByCard(operation.getTargetCard());

    accountService.makeTransaction(srcAccount, dstAccount,operation.getAmount());
  }

  @Override
  public void writeHistory(Operation operation) {
    operationRepository.save(operation);
  }
}
