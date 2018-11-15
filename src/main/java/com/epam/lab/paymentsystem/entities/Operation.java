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

  @Column(name = "number_src_card")
  private String numberSrcCard;

  @Column(name = "number_dst_card")
  private String numberDstCard;

  private String dateString;

  public Operation() {
  }

  /**
   * Constructor for operation.
   *
   * @param sourceCard    id of source card
   * @param targetCard    id of target card
   * @param amount        amount of operation
   * @param date          date of operation
   * @param numberSrcCard String
   * @param numberDstCard String
   */
  public Operation(Card sourceCard, Card targetCard, long amount,
                   LocalDateTime date, String numberDstCard, String numberSrcCard) {
    this.sourceCard = sourceCard;
    this.targetCard = targetCard;
    this.amount = amount;
    this.date = date;
    this.numberDstCard = numberDstCard;
    this.numberSrcCard = numberSrcCard;
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

  public String getNumberSrcCard() {
    return numberSrcCard;
  }

  public void setNumberSrcCard(String numberSrcCard) {
    this.numberSrcCard = numberSrcCard;
  }

  public String getNumberDstCard() {
    return numberDstCard;
  }

  public void setNumberDstCard(String numberDstCard) {
    this.numberDstCard = numberDstCard;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Operation)) {
      return false;
    }

    Operation operation = (Operation) o;

    if (getAmount() != operation.getAmount()) {
      return false;
    }
    if (getSourceCard() != null ? !getSourceCard().equals(operation.getSourceCard())
        : operation.getSourceCard() != null) {
      return false;
    }
    if (getTargetCard() != null ? !getTargetCard().equals(operation.getTargetCard())
        : operation.getTargetCard() != null) {
      return false;
    }
    if (getDate() != null ? !getDate().equals(operation.getDate())
        : operation.getDate() != null) {
      return false;
    }
    if (getNumberSrcCard() != null ? !getNumberSrcCard().equals(operation.getNumberSrcCard())
        : operation.getNumberSrcCard() != null) {
      return false;
    }
    return getNumberDstCard() != null ? getNumberDstCard().equals(operation.getNumberDstCard())
        : operation.getNumberDstCard() == null;
  }

  @Override
  public int hashCode() {
    int result = getSourceCard() != null ? getSourceCard().hashCode() : 0;
    result = 31 * result + (getTargetCard() != null ? getTargetCard().hashCode() : 0);
    result = 31 * result + (int) (getAmount() ^ (getAmount() >>> 32));
    result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
    result = 31 * result + (getNumberSrcCard() != null ? getNumberSrcCard().hashCode() : 0);
    result = 31 * result + (getNumberDstCard() != null ? getNumberDstCard().hashCode() : 0);
    return result;
  }

  public String getDateString() {
    return dateString;
  }

  public void setDateString(String dateString) {
    this.dateString = dateString;
  }
}
