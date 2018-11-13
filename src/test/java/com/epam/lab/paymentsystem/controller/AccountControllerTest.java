package com.epam.lab.paymentsystem.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AccountControllerTest extends AbstractControllerTest {
  private static final String ACCOUNT_VIEW_NAME = "addAccount";

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
    long actualId = account2.getId() + 1;
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
    mockMvc.perform(
        post(
            "/{userLogin}/account/{accountId}/unblock",
            user.getLogin(),
            account2.getId()
        )
    )
        .andExpect(status().is(302));
    assertTrue(
        accountRepository.getAccountById(account2.getId()).getIsActive(),
        "Account should be unblocked!"
    );
  }
}
