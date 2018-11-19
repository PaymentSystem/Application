package com.epam.lab.paymentsystem.utility.converter;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.Card;
import com.epam.lab.paymentsystem.entities.User;

/**
 * Utility class provides data transfer between Web and the business layer.
 * All object are transformed by this class before they save in the repository.
 */
public final class TransformerToEntity {

  private TransformerToEntity() {
  }

  /**
   * Creates a copy of the object from Web.
   *
   * @param user from html page
   * @return new user object
   */
  public static User convertUser(UserDto user) {
    User convertedUser = new User();
    convertedUser.setId(user.getId());
    convertedUser.setLogin(user.getLogin());
    convertedUser.setName(user.getName());
    convertedUser.setPassword(user.getPassword());
    convertedUser.setRole(user.getRole());
    return convertedUser;
  }

  /**
   * Creates a copy of the object from Web.
   *
   * @param account from html page
   * @return new account object
   */
  public static Account convertAccount(AccountDto account) {
    Account convertedAccount = new Account();
    convertedAccount.setId(account.getId());
    convertedAccount.setUser(account.getUser());
    convertedAccount.setLabel(account.getLabel());
    convertedAccount.setIsActive(account.getActive());
    convertedAccount.setAmount(account.getAmount());
    return convertedAccount;
  }
}
