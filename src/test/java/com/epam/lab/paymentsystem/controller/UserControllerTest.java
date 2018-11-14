package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.entities.enums.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class UserControllerTest extends AbstractControllerTest {

  @BeforeEach
  public void setUpEncoder() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  }

  @Test
  public void testWelcomePage() throws Exception {
    mockMvc.perform(
        get("/")
    )
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(content().contentType("text/html;charset=UTF-8"));
  }

  @Test
  public void testUserPage() throws Exception {
    mockMvc.perform(
        get("/{userLogin}", user.getLogin())
    )
        .andExpect(status().isOk())
        .andExpect(view().name("user"));
  }

  @Test
  public void testAddUserPage() throws Exception {
    mockMvc.perform(
        get("/registration")
    )
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andExpect(content().contentType("text/html;charset=UTF-8"));
  }

  @Test
  public void testBlockUser() throws Exception {
    mockMvc.perform(
        post("/{userLogin}/block", user.getLogin())
    )
        .andExpect(status().is(302));
    assertEquals(
        roleRepository.getRoleByRoleStatus(Roles.BLOCKED),
        userRepository.getUserByLogin(user.getLogin()).getRole(),
        "User role status should be 'BLOCKED' after test!"
    );
  }

  @Test
  public void testUnblockUser() throws Exception {
    String userLogin = "testBlocked";
    mockMvc.perform(
        post("/{userLogin}/unblock", userLogin)
    )
        .andExpect(status().is(302));
    assertEquals(
        roleRepository.getRoleByRoleStatus(Roles.USER),
        userRepository.getUserByLogin(userLogin).getRole(),
        "User role status should be 'USER' after test!"
    );
  }

  @Test
  public void testAddUserCreatesNewUser() throws Exception {
    String expectedLogin = "addTest";
    mockMvc.perform(
        post("/addUser")
            .param("login", expectedLogin)
            .param("name", "addTest")
            .param("password", "addTest")
    )
        .andExpect(status().is(302));
    assertNotNull(
        userRepository.getUserByLogin(expectedLogin),
        "User " + expectedLogin + " should exist after test!");
  }

  @Test
  public void testAddUserFailsWithNotUniqueLogin() throws Exception {
    mockMvc.perform(
        post("/addUser")
            .param("login", "test")
            .param("name", "addTest")
            .param("password", "addTest")
    )
        .andExpect(status().isOk())
        .andExpect(model().attribute(
            "messageException",
            "Login already exists")
        );
  }
}
