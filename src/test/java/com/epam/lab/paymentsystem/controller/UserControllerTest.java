package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.configuration.DispatcherConfiguration;
import com.epam.lab.paymentsystem.configuration.H2TestConfiguration;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.repository.RoleRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
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
public class UserControllerTest {

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
  @Autowired
  private H2TestDataInitializer h2TestDataInitializer;
  private BCryptPasswordEncoder encoder;

  @BeforeEach
  public void setUp() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    h2TestDataInitializer.init();

    user = userRepository.getUserByLogin("test");
    when(userService.getCurrentUserLogin()).thenReturn(user.getLogin());
    encoder = new BCryptPasswordEncoder();

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
    assertEquals(roleRepository.getRoleByRoleStatus(Roles.BLOCKED),
        userRepository.getUserByLogin(user.getLogin()).getRole());
  }

  @Test
  public void testUnblockUser() throws Exception {
    mockMvc.perform(
        post("/{userLogin}/unblock", user.getLogin())
    )
        .andExpect(status().is(302));
    assertEquals(roleRepository.getRoleByRoleStatus(Roles.USER),
        userRepository.getUserByLogin(user.getLogin()).getRole());
  }

  @Test
  public void testAddUserCreatesNewUser() throws Exception {
    mockMvc.perform(
        post("/addUser")
            .param("login", "addTest")
            .param("name", "addTest")
            .param("password", "addTest")
    )
        .andExpect(status().is(302));
    assertNotNull(userRepository.getUserByLogin("addTest"));
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
        .andExpect(model().attribute("messageException", "Login already exists"));
  }
}
