package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.configuration.DispatcherConfiguration;
import com.epam.lab.paymentsystem.configuration.H2TestConfiguration;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.repository.AccountRepository;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    DispatcherConfiguration.class,
    H2TestConfiguration.class
})
@WebAppConfiguration
public class AccountControllerTest {
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private AccountRepository accountRepository;
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
    when(userService.getCurrentUserLogin()).thenReturn(user.getLogin());

    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void testAddAccountFormCreatesNewAccount() throws Exception {
    mockMvc.perform(
        post("/{userLogin}/addAccount", user.getLogin())
            .param("amount", "1000")
            .param("label", "visa")
    )
        .andExpect(status().is(302))
        .andExpect(view().name("redirect:/{userLogin}"))
        .andExpect(redirectedUrl("/" + user.getLogin()));
    assertEquals("visa",
        accountRepository.getAccountById(2).getLabel());
  }

  @Test
  public void testAddAccountFormWithInvalidParams() throws Exception {
    mockMvc.perform(
        post("/{userLogin}/addAccount", user.getLogin())
            .param("amount", "-200")
    )
        .andExpect(status().isOk())
        .andExpect(view().name("addAccount"))
        .andExpect(model().attribute("messageAccount",
            "Amount should be positive"));
  }

  @Test
  public void testAddAccountPage() throws Exception {
    mockMvc.perform(
        get("/{userLogin}/addAccount", user.getLogin())
    )
        .andExpect(status().isOk())
        .andExpect(view().name("addAccount"));
  }

  @Test
  public void testBlockAccount() throws Exception {
    mockMvc.perform(
        post("/{userLogin}/account/{accountId}/block",
            user.getLogin(), account.getId())
    )
        .andExpect(status().is(302));
    assertFalse(accountRepository.getAccountById(account.getId()).getIsActive());
  }

  @Test
  public void testUnblockAccount() throws Exception {
    mockMvc.perform(
        post("/{userLogin}/account/{accountId}/unblock",
            user.getLogin(), account.getId())
    )
        .andExpect(status().is(302));
    assertTrue(accountRepository
        .getAccountById(account.getId())
        .getIsActive());
  }
}
