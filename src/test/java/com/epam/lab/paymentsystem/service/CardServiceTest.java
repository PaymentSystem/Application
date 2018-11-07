package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.repository.CardRepository;
import com.epam.lab.paymentsystem.service.impl.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
    private long accountId;
    private Account account;
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
        accountId = 1;
        account = new Account();
        cards = new ArrayList<>();

        user = new User();
        user.setLogin("test");

        card = new Card(account, user, "test", true);
        cardDto = new CardDto(0, accountId, "test", "test", true);
    }

    @Test
    public void testGetAllCardsByAccountIdReturnsCardsList() {
        when(accountService.getAccountById(accountId)).thenReturn(account);
        when(cardRepository.getAllByAccount(account)).thenReturn(cards);
        assertEquals(cards, cardService.getAllCardsByAccountId(accountId));
    }

    @Test
    public void testCreateCardReturnsCard() {
        when(accountService.getAccountById(accountId)).thenReturn(account);
        when(userService.getUserByLogin(user.getLogin())).thenReturn(user);
        when(cardRepository.save(card)).thenReturn(card);
        assertEquals(card, cardService.createCard(cardDto));
    }
}
