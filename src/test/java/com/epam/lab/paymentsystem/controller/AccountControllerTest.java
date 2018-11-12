package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.configuration.DispatcherConfiguration;
import com.epam.lab.paymentsystem.configuration.H2TestConfiguration;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.repository.AccountRepository;
import com.epam.lab.paymentsystem.repository.UserRepository;
import com.epam.lab.paymentsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
public class AccountControllerTest extends AbstractControllerTest {
  private static final String ACCOUNT_VIEW_NAME = "addAccount";
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

  @BeforeEach
  public void setUpFields() {
    user = userRepository.getUserByLogin("test");
    account = accountRepository.getAccountById(1);
    when(userService.getCurrentUserLogin()).thenReturn(user.getLogin());

    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void testAddAccountFormCreatesNewAccount() throws Exception {
    String expectedLabel = "visa";
    mockMvc.perform(
        post("/{userLogin}/addAccount", user.getLogin())
            .param("amount", "1000")
            .param("label", expectedLabel)
    )
        .andExpect(status().is(302))
        .andExpect(view().name("redirect:/{userLogin}"))
        .andExpect(redirectedUrl("/" + user.getLogin()));
    int actualId = 3;
    assertEquals(
        expectedLabel,
        accountRepository.getAccountById(actualId).getLabel(),
        "Labels should be the same after test!"
    );
  }

  @Test
  public void testAddAccountFormWithInvalidParams() throws Exception {
    mockMvc.perform(
        post("/{userLogin}/addAccount", user.getLogin())
            .param("amount", "-200")
    )
        .andExpect(status().isOk())
        .andExpect(view().name(ACCOUNT_VIEW_NAME))
        .andExpect(
            model().attribute(
                "messageAccount",
                "Amount should be positive"
            )
        );
  }

  @Test
  public void testAddAccountPage() throws Exception {
    mockMvc.perform(
        get("/{userLogin}/addAccount", user.getLogin())
    )
        .andExpect(status().isOk())
        .andExpect(view().name(ACCOUNT_VIEW_NAME));
  }

  @Test
  public void testBlockAccount() throws Exception {
    mockMvc.perform(
        post(
            "/{userLogin}/account/{accountId}/block",
            user.getLogin(),
            account.getId()
        )
    )
        .andExpect(status().is(302));
    assertFalse(
        accountRepository.getAccountById(account.getId()).getIsActive(),
        "Account should be blocked!"
    );
  }

  @Test
  public void testUnblockAccount() throws Exception {
    int blockedAccountId = 2;
    mockMvc.perform(
        post(
            "/{userLogin}/account/{accountId}/unblock",
            user.getLogin(),
            blockedAccountId
        )
    )
        .andExpect(status().is(302));
    assertTrue(
        accountRepository.getAccountById(blockedAccountId).getIsActive(),
        "Account should be unblocked!"
    );
  }
}
