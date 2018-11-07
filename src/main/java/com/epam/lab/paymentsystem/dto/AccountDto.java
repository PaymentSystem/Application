package com.epam.lab.paymentsystem.dto;

import com.epam.lab.paymentsystem.entities.User;

public class AccountDto {
  private long id;
  private User user;
  private String label;
  private long amount;
  private boolean isActive;

  public AccountDto() {
  }

  /**
   * Constructor with parameters for account dto.
   *
   * @param id       account's id
   * @param user     account linked to this user
   * @param label    account's label
   * @param amount   account's amount
   * @param isActive boolean flag
   */
  public AccountDto(long id, User user, String label, long amount, boolean isActive) {
    this.id = id;
    this.user = user;
    this.label = label;
    this.amount = amount;
    this.isActive = isActive;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public boolean getActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
