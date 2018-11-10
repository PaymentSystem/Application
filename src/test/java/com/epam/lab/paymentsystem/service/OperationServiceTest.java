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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.epam.lab.paymentsystem.dto.OperationDto;
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
  private OperationDto operationDto;
  private Account accountSrc;
  private Account accountDst;
  private long cardId;
  private long cardSrcId;
  private long cardDstId;
  private long prevSrcAmount;
  private long prevDstAmount;
  private long transferAmount;

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
    cardSrcId = 1;
    cardDstId = 2;
    cards = new ArrayList<>();
    operations = new ArrayList<>();

    prevSrcAmount = 500;
    prevDstAmount = 100;
    transferAmount = 200;

    accountSrc = new Account(null, "accountSrc", prevSrcAmount, true);
    accountDst = new Account(null, "accountDst", prevDstAmount, true);
    cardSrc = new Card(accountSrc, null, "cardSrc", true);
    cardSrc.setId(cardSrcId);
    cardDst = new Card(accountDst, null, "cardDst", true);
    cardDst.setId(cardDstId);
    operation = new Operation(cardSrc, cardDst, transferAmount, null);

    operationDto = new OperationDto(cardSrcId, cardDstId, transferAmount);

    login = "test";
    user = new User();
    user.setLogin(login);
  }

  @Test
  public void testGetAllOperationsReturnsOperationsList() {
    when(userService.getCurrentUserLogin()).thenReturn(user.getLogin());
    when(cardService.getAllCardsByLogin(user.getLogin())).thenReturn(cards);
    when(operationRepository.getAllBySourceCardIsInOrTargetCardIsIn(cards, cards))
        .thenReturn(operations);
    assertEquals(operations, operationService.getAllOperations());
  }

  @Test
  public void testMakePayment() {
    Answer<Card> cardAnswer = invocationOnMock -> {
      Long idCard = (Long) invocationOnMock.getArgument(0);
      return idCard.equals(cardSrcId) ? cardSrc : cardDst;
    };
    when(cardService.getCardById(anyLong())).thenAnswer(cardAnswer);

    Answer<Void> makeTransactionAnswer = invocation -> {
      Account srcAccount = (Account) invocation.getArgument(0);
      Account dstAccount = (Account) invocation.getArgument(1);
      Long amount = (Long) invocation.getArgument(2);

      srcAccount.setAmount(srcAccount.getAmount() - amount);
      dstAccount.setAmount(dstAccount.getAmount() + amount);
      return null;
    };
    doAnswer(makeTransactionAnswer).when(accountService).makeTransaction(
            any(Account.class), any(Account.class), any(Long.class));

    operationService.makePayment(operationDto);
    assertEquals(prevSrcAmount - operation.getAmount(), cardSrc.getAccount().getAmount());
  }

  @Test
  public void testGetAllOperationsByAccountReturnsOperationsList() {
    when(cardService.getAllCardsByAccountId(accountId)).thenReturn(cards);
    when(operationRepository.getAllBySourceCardIsInOrTargetCardIsIn(cards, cards))
        .thenReturn(operations);
    assertEquals(operations, operationService.getAllOperationsByAccount(accountId));
  }

  @Test
  public void testGetAllOperationsByCardReturnsOperationsList() {
    assertEquals(operations, operationService.getAllOperationsByCard(cardId));
  }
}
