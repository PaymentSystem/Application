package com.epam.lab.paymentsystem.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cards")
public class Card extends AbstractEntity {
  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "id_account")
  private Account account;

  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "id_user")
  private User user;

  @Column(name = "label")
  private String label;

  @Column(name = "is_active")
  private boolean isActive;

  public Card() {}

  /**
   * Constructor for card.
   *
   * @param account id of account
   * @param user userId of card
   * @param label label
   * @param isActive boolean flag
   */
  public Card(Account account, User user, String label, boolean isActive) {
    this.account = account;
    this.user = user;
    this.label = label;
    this.isActive = isActive;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + user.hashCode();
    result = 31 * result + account.hashCode();
    result = 31 * result + label.hashCode();
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

    return card.user.equals(user)
        && card.account.equals(account)
        && label.equals(label)
        && card.isActive == isActive;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
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

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean active) {
    isActive = active;
  }
}
