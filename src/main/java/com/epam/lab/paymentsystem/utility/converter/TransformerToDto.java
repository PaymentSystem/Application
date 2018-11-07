package com.epam.lab.paymentsystem.utility.converter;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.User;

public final class TransformerToDto {
  /**
   * Creates a dto of the entity from database.
   *
   * @param user from html page
   * @return new user object
   */
  public static UserDto convertUser(User user) {
    UserDto convertedUser = new UserDto();
    convertedUser.setId(user.getId());
    convertedUser.setLogin(user.getLogin());
    convertedUser.setName(user.getName());
    convertedUser.setPassword(user.getPassword());
    convertedUser.setRole(user.getRole());
    return convertedUser;
  }

  /**
   * Creates a dto of the entity from the database.
   *
   * @param account from html page
   * @return new account object
   */
  public static AccountDto convertAccount(Account account) {
    AccountDto convertedAccount = new AccountDto();
    convertedAccount.setId(account.getId());
    convertedAccount.setUser(account.getUser());
    convertedAccount.setLabel(account.getLabel());
    convertedAccount.setActive(account.getIsActive());
    convertedAccount.setAmount(account.getAmount());
    return convertedAccount;
  }
}
