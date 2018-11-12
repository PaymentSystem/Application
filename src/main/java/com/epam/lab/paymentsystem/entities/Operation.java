package com.epam.lab.paymentsystem.entities;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "operations")
public class Operation extends AbstractEntity {
  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "id_src_card")
  private Card sourceCard;

  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "id_dst_card")
  private Card targetCard;

  @Column(name = "transfer_amount")
  private long amount;

  @Column(name = "date")
  private LocalDateTime date;

  public Operation() {
  }

  /**
   * Constructor for operation.
   *
   * @param sourceCard id of source card
   * @param targetCard id of target card
   * @param amount     amount of operation
   * @param date       date of operation
   */
  public Operation(Card sourceCard, Card targetCard, long amount, LocalDateTime date) {
    this.sourceCard = sourceCard;
    this.targetCard = targetCard;
    this.amount = amount;
    this.date = date;
  }

  public Card getSourceCard() {
    return sourceCard;
  }

  public void setSourceCard(Card sourceCard) {
    this.sourceCard = sourceCard;
  }

  public Card getTargetCard() {
    return targetCard;
  }

  public void setTargetCard(Card targetCard) {
    this.targetCard = targetCard;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + sourceCard.hashCode();
    result = 31 * result + targetCard.hashCode();
    result = 31 * result + Long.valueOf(amount).hashCode();
    result = 31 * result + date.hashCode();

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Operation)) {
      return false;
    }

    Operation operation = (Operation) obj;

    return operation.sourceCard.equals(sourceCard)
        && operation.targetCard.equals(targetCard)
        && operation.amount == amount
        && operation.date.equals(date);
  }
}
