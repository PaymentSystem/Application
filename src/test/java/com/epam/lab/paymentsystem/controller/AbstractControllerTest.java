package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.configuration.DispatcherConfiguration;
import com.epam.lab.paymentsystem.configuration.H2TestConfiguration;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Role;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.repository.AccountRepository;
import com.epam.lab.paymentsystem.repository.CardRepository;
import com.epam.lab.paymentsystem.repository.RoleRepository;
import com.epam.lab.paymentsystem.repository.UserRepository;
import com.epam.lab.paymentsystem.service.UserService;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    DispatcherConfiguration.class,
    H2TestConfiguration.class
})
@WebAppConfiguration
public abstract class AbstractControllerTest {
  @Autowired
  public ApplicationContext applicationContext;
  @Autowired
  protected WebApplicationContext wac;
  @Autowired
  protected UserRepository userRepository;
  @Autowired
  protected UserService userService;
  @Autowired
  protected AccountRepository accountRepository;
  @Autowired
  protected CardRepository cardRepository;
  @Autowired
  protected RoleRepository roleRepository;
  protected MockMvc mockMvc;
  protected User user;
  protected Account account;
  protected User user2;
  protected Account account2;
  protected Card card;

  @BeforeEach
  public void setUp() throws SQLException {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    init();

    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    when(userService.getCurrentUserLogin()).thenReturn(user.getLogin());
  }


  @AfterEach
  public void cleanUp() {
    clean();
  }


  public void init() {
    roleRepository.save(new Role(1, Roles.ADMIN));
    Role roleBlocked = roleRepository.save(new Role(3, Roles.BLOCKED));
    Role role = roleRepository.save(new Role(2, Roles.USER));
    user = userRepository.save(new User("test", "test", "test", role));
    user2 = userRepository.save(
        new User("testBlocked", "testBlocked", "testBlocked", roleBlocked)
    );
    account = accountRepository.save(new Account(user, "acc", 1000, true));
    account2 = accountRepository.save(new Account(user, "acc", 1000, false));
    card = cardRepository.save(new Card(account, user, "card", true));
  }

  public void clean() {
    cardRepository.deleteAll();
    accountRepository.deleteAll();
    userRepository.deleteAll();
  }
}


