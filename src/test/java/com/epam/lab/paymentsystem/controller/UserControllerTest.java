package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.configuration.DispatcherConfiguration;
import com.epam.lab.paymentsystem.configuration.H2TestConfiguration;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.repository.RoleRepository;
import com.epam.lab.paymentsystem.repository.UserRepository;
import com.epam.lab.paymentsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    DispatcherConfiguration.class,
    H2TestConfiguration.class
})
@WebAppConfiguration
public class UserControllerTest extends AbstractControllerTest {
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private RoleRepository roleRepository;
  private User user;

  @BeforeEach
  public void setUpFields() {
    user = userRepository.getUserByLogin("test");
    when(userService.getCurrentUserLogin()).thenReturn(user.getLogin());
    new BCryptPasswordEncoder();

    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
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
