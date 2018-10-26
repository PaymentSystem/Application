package com.epam.lab.paymentsystem.entities;

public class Card {

  private long id;
  private long accountId;
  private long userId;
  private boolean isActive;

  public Card() {

  }

  public Card(long id, long accountId, long userId, boolean isActive) {
    this.id = id;
    this.accountId = accountId;
    this.userId = userId;
    this.isActive = isActive;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long accountId) {
    this.accountId = accountId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
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
    result = 31 * result + Long.valueOf(userId).hashCode();
    result = 31 * result + Long.valueOf(accountId).hashCode();
    result = 31 * result + Boolean.valueOf(isActive).hashCode();

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Card)) {
      return false;
    }

    Card card = (Card) obj;

    return card.userId == userId
        && card.accountId == accountId
        && card.isActive == isActive;
  }
}
