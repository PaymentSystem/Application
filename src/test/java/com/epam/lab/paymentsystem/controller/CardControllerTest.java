package com.epam.lab.paymentsystem.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CardControllerTest extends AbstractControllerTest {

  @Test
  public void testCardListPage() throws Exception {
    mockMvc.perform(
        get(
            "/{userLogin}/account/{accId}",
            user.getLogin(),
            account.getId()
        )
    )
        .andExpect(status().isOk())
        .andExpect(view().name("account"));
  }

  @Test
  public void testAddCardPage() throws Exception {
    mockMvc.perform(
        get(
            "/{userLogin}/account/{accountId}/addCard",
            user.getLogin(),
            account.getId()
        )
    )
        .andExpect(status().isOk())
        .andExpect(view().name("addCard"));
  }

  @Test
  public void testAddCardCreatesNewCard() throws Exception {
    String expectedLabel = "visa";
    String expectedUserLogin = "test";
    mockMvc.perform(
        post(
            "/{userLogin}/account/{accountId}/addCard",
            user.getLogin(),
            account.getId()
        )
            .param("label", expectedLabel)
            .param("userLogin", expectedUserLogin)
    )
        .andExpect(status().is(302))
        .andExpect(view().name("redirect:/{userLogin}/account/{accountId}"));
    int actualId = 2;
    assertEquals(
        expectedLabel,
        cardRepository.getCardById(actualId).getLabel(),
        "Cards labels should be the same after test!"
    );
    assertEquals(
        userRepository.getUserByLogin(expectedUserLogin),
        cardRepository.getCardById(actualId).getUser(),
        "Cards user should be the same after test!"
    );
  }

  @Test
  public void testAddCardFailsWithInvalidUserLoginParam() throws Exception {
    mockMvc.perform(
        post(
            "/{userLogin}/account/{accountId}/addCard",
            user.getLogin(),
            account.getId()
        )
            .param("label", "visa")
            .param("userLogin", "noSuchUserLogin")
    )
        .andExpect(status().isOk())
        .andExpect(model().attribute(
            "messageCard",
            "No such user")
        );
  }
}
