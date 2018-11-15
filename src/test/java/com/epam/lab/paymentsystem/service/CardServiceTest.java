package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.exception.CardArgumentException;
import com.epam.lab.paymentsystem.repository.CardRepository;
import com.epam.lab.paymentsystem.service.impl.CardServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
  private long accountId;
  private String login;
  private Account account;
  private Pageable pageable;
  private Page<Card> cardsPage;
  private List<Card> cards;
  private User user;
  private Card card;
  private CardDto cardDto;

  @Mock
  private CardRepository cardRepository;

  @Mock
  private UserService userService;

  @Mock
  private AccountService accountService;

  @InjectMocks
  private CardServiceImpl cardService;

  @BeforeEach
  public void startUp() {
    MockitoAnnotations.initMocks(this);
    pageable = PageRequest.of(0, 5, Sort.Direction.ASC, "label");
    cardsPage = Page.empty(pageable);

    accountId = 1;
    login = "test";
    account = new Account();
    cards = new ArrayList<>();

    user = new User();
    user.setLogin(login);

    card = new Card(account, user, "test", true);
    cardDto = new CardDto(0, accountId, login, "test", true);
  }

  @Test
  public void testGetAllCardsByAccountIdReturnsCardsPage() {
    when(accountService.getAccountById(accountId)).thenReturn(account);
    when(cardRepository.getAllByAccount(account, pageable)).thenReturn(cardsPage);
    assertEquals(
        cardsPage,
        cardService.getAllCardsByAccountId(accountId, pageable),
        "The card page should be equal to the card page retrieved by the card service");
  }

  @Test
  public void testGetAllCardsByUserLoginReturnsCardsList() {
    when(userService.getUserByLogin(user.getLogin())).thenReturn(user);
    when(cardRepository.getAllByUser(user)).thenReturn(cards);
    assertEquals(cards, cardService.getAllCardsByLogin(login));
  }

  @Test
  public void testCreateCardReturnsCard() {
    when(accountService.getAccountById(accountId)).thenReturn(account);
    when(userService.getUserByLogin(user.getLogin())).thenReturn(user);
    when(cardRepository.save(card)).thenReturn(card);
    assertEquals(card, cardService.createCard(cardDto));
  }

  @Test
  public void testCreateCardThrowsException() {
    when(accountService.getAccountById(accountId)).thenReturn(account);
    when(userService.getUserByLogin(user.getLogin())).thenReturn(null);
    assertThrows(
        CardArgumentException.class, () -> cardService.createCard(cardDto), "No such user");
  }
}
