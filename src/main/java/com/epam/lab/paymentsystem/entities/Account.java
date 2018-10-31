package com.epam.lab.paymentsystem.entities;

public class Account extends AbstractEntity {

  private double amount;
  private boolean isActive;

  public Account() {
  }

  /**
   * Constructor for account.
   *
   * @param amount   amount of account
   * @param isActive boolean flag
   */
  public Account(double amount, boolean isActive) {
    this.amount = amount;
    this.isActive = isActive;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + Double.valueOf(amount).hashCode();
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

    return account.amount == amount
        && account.isActive == isActive;
  }
}
