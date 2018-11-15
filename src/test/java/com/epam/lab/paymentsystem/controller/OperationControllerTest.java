package com.epam.lab.paymentsystem.controller;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class OperationControllerTest extends AbstractControllerTest {

  private static final String EXPECTED_HISTORY_VIEW_NAME = "history";
  private static final String EXPECTED_OPERATION_VIEW_NAME = "operation";

  @Test
  public void testOperationPage() throws Exception {
    mockMvc.perform(
        get("/{userLogin}/operation",
        user.getLogin())
    )
        .andExpect(status().isOk())
        .andExpect(view().name(EXPECTED_OPERATION_VIEW_NAME));
  }

  @Test
  public void testUserHistoryPage() throws Exception {
    mockMvc.perform(
        get("/{userLogin}/history",
        user.getLogin())
    )
        .andExpect(status().isOk())
        .andExpect(view().name(EXPECTED_HISTORY_VIEW_NAME));
  }

  @Test
  public void testAccountHistoryPage() throws Exception {
    mockMvc.perform(
        get("/{userLogin}/history/{accountId}",
            user.getLogin(),
            account.getId())
    )
        .andExpect(status().isOk())
        .andExpect(view().name(EXPECTED_HISTORY_VIEW_NAME));
  }

  @Test
  public void testCardHistoryPage() throws Exception {
    mockMvc.perform(
        get("/{userLogin}/account/{accountId}/history/{cardId}",
            user.getLogin(),
            account.getId(),
            card.getId())
    )
        .andExpect(status().isOk())
        .andExpect(view().name(EXPECTED_HISTORY_VIEW_NAME));
  }
}
