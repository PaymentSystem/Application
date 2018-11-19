package com.epam.lab.paymentsystem.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account extends AbstractEntity {
  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "id_user")
  private User user;

  @Column(name = "label")
  private String label;

  @Column(name = "amount")
  private long amount;

  @Column(name = "is_active")
  private boolean isActive;

  public Account() {
  }

  /**
   * Constructor for account.
   *
   * @param user     user
   * @param label    label
   * @param amount   amount of account
   * @param isActive boolean flag
   */
  public Account(User user, String label, long amount, boolean isActive) {
    this.user = user;
    this.label = label;
    this.amount = amount;
    this.isActive = isActive;
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

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean active) {
    isActive = active;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + user.hashCode();
    result = 31 * result + label.hashCode();
    result = 31 * result + Long.valueOf(amount).hashCode();
    result = 31 * result + Boolean.valueOf(isActive).hashCode();

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Account)) {
      return false;
    }

    Account account = (Account) obj;

    return account.user.equals(user)
        && account.label.equals(label)
        && account.amount == amount
        && account.isActive == isActive;
  }
}
