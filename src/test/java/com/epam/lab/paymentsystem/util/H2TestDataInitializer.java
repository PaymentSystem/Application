package com.epam.lab.paymentsystem.util;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.Role;
import com.epam.lab.paymentsystem.entities.User;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import com.epam.lab.paymentsystem.repository.AccountRepository;
import com.epam.lab.paymentsystem.repository.CardRepository;
import com.epam.lab.paymentsystem.repository.RoleRepository;
import com.epam.lab.paymentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class H2TestDataInitializer {
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private CardRepository cardRepository;

  public void init() {
    roleRepository.save(new Role(1, Roles.ADMIN));
    Role roleBlocked = roleRepository.save(new Role(3, Roles.BLOCKED));
    Role role = roleRepository.save(new Role(2, Roles.USER));
    User user = userRepository.save(new User("test", "test", "test", role));
    User user2 = userRepository.save(
        new User("testBlocked", "testBlocked", "testBlocked", roleBlocked)
    );
    Account account = accountRepository.save(new Account(user, "acc", 1000, true));
    Account account2 = accountRepository.save(new Account(user, "acc", 1000, false));
    Card card = cardRepository.save(new Card(account, user, "card", true));
    card.setId(1);
  }

  public void clean() {
    cardRepository.deleteAll();
    accountRepository.deleteAll();
    userRepository.deleteAll();
  }
}
