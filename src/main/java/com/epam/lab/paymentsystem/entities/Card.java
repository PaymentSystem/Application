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

  @Column(name = "card_number")
  private String cardNumber;

  public Card() {
  }

  /**
   * Constructor for card.
   *
   * @param account  id of account
   * @param user     userId of card
   * @param label    label
   * @param isActive boolean flag
   */
  public Card(Account account, User user, String label, boolean isActive, String cardNumber) {
    this.account = account;
    this.user = user;
    this.label = label;
    this.isActive = isActive;
    this.cardNumber = cardNumber;
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

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Card)) {
      return false;
    }

    Card card = (Card) o;

    if (getIsActive() != card.getIsActive()) {
      return false;
    }
    if (getAccount() != null ? !getAccount().equals(card.getAccount())
        : card.getAccount() != null) {
      return false;
    }
    if (getUser() != null ? !getUser().equals(card.getUser()) : card.getUser() != null) {
      return false;
    }
    if (getLabel() != null ? !getLabel().equals(card.getLabel()) : card.getLabel() != null) {
      return false;
    }
    return getCardNumber() != null ? getCardNumber().equals(card.getCardNumber())
        : card.getCardNumber() == null;
  }

  @Override
  public int hashCode() {
    int result = getAccount() != null ? getAccount().hashCode() : 0;
    result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
    result = 31 * result + (getLabel() != null ? getLabel().hashCode() : 0);
    result = 31 * result + (getIsActive() ? 1 : 0);
    result = 31 * result + (getCardNumber() != null ? getCardNumber().hashCode() : 0);
    return result;
  }
}
