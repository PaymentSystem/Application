package com.epam.lab.paymentsystem.service;
/*
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.repository.CardRepository;
import com.epam.lab.paymentsystem.repository.OperationRepository;
import com.epam.lab.paymentsystem.service.impl.CardServiceImpl;
import com.epam.lab.paymentsystem.service.impl.OperationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {
  private long accountId;
  private long cardId;
  private Operation operation;
  private List<Card> cards;
  private List<Operation> operations;
  private String login;
  private User user;

  @Mock
  private OperationRepository operationRepository;

  @Mock
  private UserService userService;

  @Mock
  private CardService cardService;

  @InjectMocks
  private OperationServiceImpl operationService;

  @BeforeEach
  public void startUp() {
    MockitoAnnotations.initMocks(this);
    accountId = 1;
    cardId = 1;
    operation = new Operation();
    cards = new ArrayList<>();
    operations = new ArrayList<>();
    login = "test";
    user = new User();
    user.setLogin(login);
  }

  @Test
  public void testGetAllOperationsReturnsOperationsList(){
    when(userService.getCurrentUserLogin()).thenReturn(user.getLogin());
    when(cardService.getAllCardsByLogin(user.getLogin())).thenReturn(cards);
    when(operationRepository.getAllBySourceCardIsIn(cards)).thenReturn(operations);
    assertEquals(operations, operationService.getAllOperations());
  }
}*/


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.repository.OperationRepository;
import com.epam.lab.paymentsystem.service.impl.OperationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {
  private long accountId;
  private String login;
  private List<Card> cards;
  private List<Operation> operations;
  private User user;
  private Card cardSrc;
  private Card cardDst;
  private Operation operation;
  private Account accountSrc;
  private Account accountDst;
  private long cardId;
  private long prevSrcAmount;
  private long prevDstAmount;

  @Mock
  private OperationRepository operationRepository;

  @Mock
  private UserService userService;

  @Mock
  private AccountService accountService;

  @Mock
  private CardService cardService;

  @InjectMocks
  private OperationServiceImpl operationService;

  @BeforeEach
  public void startUp() {
    MockitoAnnotations.initMocks(this);
    accountId = 1;
    cardId = 1;
    cards = new ArrayList<>();
    operations = new ArrayList<>();

    prevSrcAmount = 500;
    prevDstAmount = 100;

    accountSrc = new Account(null, "accountSrc", prevSrcAmount, true);
    accountDst = new Account(null, "accountDst", prevDstAmount, true);
    cardSrc = new Card(accountSrc, null, "cardSrc", true);
    cardSrc.setId(1);
    cardDst = new Card(accountDst, null, "cardDst", true);
    cardDst.setId(2);
    operation = new Operation(cardSrc, cardDst, 200, null);

    login = "test";
    user = new User();
    user.setLogin(login);
  }

  @Test
  public void testGetAllOperationsReturnsOperationsList() {
    when(userService.getCurrentUserLogin()).thenReturn(user.getLogin());
    when(cardService.getAllCardsByLogin(user.getLogin())).thenReturn(cards);
    when(operationRepository.getAllBySourceCardIsIn(cards)).thenReturn(operations);
    assertEquals(operations, operationService.getAllOperations());
  }

  @Test
  public void testMakePayment() {
    Answer<Card> cardAnswer = invocationOnMock -> {
      Long idCard = (Long) invocationOnMock.getArgument(0);
      if (idCard.equals(1L)) {
        return cardSrc;
      } else {
        return cardDst;
      }
    };
    when(cardService.getCardById(anyLong())).thenAnswer(cardAnswer);

    /* We attempt to use this for make transaction
    Answer<Void> makeTransactionAnswer = invocation -> {
      Account srcAccount = (Account) invocation.getArgument(0);
      Account dstAccount = (Account) invocation.getArgument(1);
      Long amount = (Long) invocation.getArgument(2);

      srcAccount.setAmount(srcAccount.getAmount() - amount);
      dstAccount.setAmount(dstAccount.getAmount() + amount);
      return null;
    };

    //This not works (syntax error)
    when(accountService.makeTransaction(accountSrc, accountDst, operation.getAmount())).thenAnswer(makeTransactionAnswer);
    */

    operationService.makePayment(operation);
    //assertEquals(prevSrcAmount - operation.getAmount(), cardSrc.getAccount().getAmount());
  }

  @Test
  public void testGetAllOperationsByAccountReturnsOperationsList() {
    when(cardService.getAllCardsByAccountId(accountId)).thenReturn(cards);
    when(operationRepository.getAllBySourceCardIsIn(cards)).thenReturn(operations);
    assertEquals(operations, operationService.getAllOperationsByAccount(accountId));
  }

  @Test
  public void testGetAllOperationsByCardReturnsOperationsList() {
    assertEquals(operations, operationService.getAllOperationsByCard(cardId));
  }

  @Test
  public void testWriteHistoryReturnsOperation() {
    assertEquals(operation, operationService.writeHistory(operation));
  }
}
