package com.epam.lab.paymentsystem.service.impl;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.repository.AccountRepository;
import com.epam.lab.paymentsystem.repository.CardRepository;
import com.epam.lab.paymentsystem.repository.OperationRepository;
import com.epam.lab.paymentsystem.service.OperationService;
import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements OperationService {


  @Autowired
  private OperationRepository operationRepository;

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private AccountRepository accountRepository;


  @Override
  public void makePayment(Operation operation) {
//    Account testAccountOne = new Account();
//    testAccountOne.setAmount(5000);
//
//    Card testCardOne = new Card();
//    testCardOne.setAccount(testAccountOne);
//
//    cardRepository.save(testCardOne);
//
//    Account testAccountTwo = new Account();
//    testAccountTwo.setAmount(1000);
//
//    Card testCardTwo = new Card();
//    testCardTwo.setAccount(testAccountTwo);
//
//    cardRepository.save(testCardTwo);


//
//    Account srcAccount = operationRepository.getAccountByCard(operation.getSourceCard());
//    Account dstAccount = operationRepository.getAccountByCard(operation.getTargetCard());
//
//    accountService.makeTransaction(srcAccount, dstAccount,operation.getAmount());


    Card sourceCard = cardRepository.getCardById(operation.getSourceCard().getId());
    Card destinationCard = cardRepository.getCardById(operation.getTargetCard().getId());
    Account sourceAccount = sourceCard.getAccount();
    Account destinationAccount = destinationCard.getAccount();

    if (sourceAccount.getAmount() < operation.getAmount() || operation.getAmount() < 0) {
      throw new UnsupportedOperationException("Too few money");
    }

    sourceAccount.setAmount(sourceAccount.getAmount() - operation.getAmount());
    destinationAccount.setAmount(destinationAccount.getAmount() + operation.getAmount());
    accountRepository.save(sourceAccount);
    accountRepository.save(destinationAccount);

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
