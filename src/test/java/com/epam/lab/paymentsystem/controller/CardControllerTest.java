package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.configuration.DispatcherConfiguration;
import com.epam.lab.paymentsystem.configuration.H2TestConfiguration;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.repository.AccountRepository;
import com.epam.lab.paymentsystem.repository.CardRepository;
import com.epam.lab.paymentsystem.repository.UserRepository;
import com.epam.lab.paymentsystem.service.UserService;
import com.epam.lab.paymentsystem.util.H2TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    DispatcherConfiguration.class,
    H2TestConfiguration.class
})
@WebAppConfiguration
public class CardControllerTest {
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private CardRepository cardRepository;
  private User user;
  private Account account;
  @Autowired
  private H2TestDataInitializer h2TestDataInitializer;

  @BeforeEach
  public void setUp() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    h2TestDataInitializer.init();

    user = userRepository.getUserByLogin("test");
    account = accountRepository.getAccountById(1);
    cardRepository.getCardById(1);
    when(userService.getCurrentUserLogin()).thenReturn(user.getLogin());

    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void testCardListPage() throws Exception {
    mockMvc.perform(
        get("/{userLogin}/account/{accId}",
            user.getLogin(), account.getId())
    )
        .andExpect(status().isOk())
        .andExpect(view().name("account"));
  }

  @Test
  public void testAddCardPage() throws Exception {
    mockMvc.perform(
        get("/{userLogin}/account/{accountId}/addCard",
            user.getLogin(), account.getId())
    )
        .andExpect(status().isOk())
        .andExpect(view().name("addCard"));
  }

  @Test
  public void testAddCardCreatesNewCard() throws Exception {
    mockMvc.perform(
        post("/{userLogin}/account/{accountId}/addCard",
            user.getLogin(), account.getId())
            .param("label", "visa")
            .param("login", "test")
    )
        .andExpect(status().is(302))
        .andExpect(view().name("redirect:/{userLogin}/account/{accountId}"));
    assertEquals("visa", cardRepository.getCardById(2).getLabel());
    assertEquals(userRepository.getUserByLogin("test"),
        cardRepository.getCardById(2).getUser());
  }

  @Test
  public void testAddCardCreatesNewCard0() throws Exception {
    mockMvc.perform(
        post("/{userLogin}/account/{accountId}/addCard",
            user.getLogin(), account.getId())
            .param("label", "visa")
            .param("userLogin", "TAKOGO NET")
    )
        .andExpect(status().isOk())
        .andExpect(model().attribute("messageCard",
            "No such user"));
  }
}
