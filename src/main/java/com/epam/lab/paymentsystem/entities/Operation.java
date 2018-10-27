package com.epam.lab.paymentsystem.entities;

import java.time.LocalDateTime;

public class Operation {

  private long id;
  private long sourceId;
  private long targetId;
  private double amount;
  private LocalDateTime date;

  public Operation() {

  }

  /**
   * Constructor for operation.
   *
   * @param id id of operation
   * @param sourceId id of source card
   * @param targetId id of target card
   * @param amount amount of operation
   * @param date date of operation
   */
  public Operation(long id, long sourceId, long targetId, double amount, LocalDateTime date) {
    this.id = id;
    this.sourceId = sourceId;
    this.targetId = targetId;
    this.amount = amount;
    this.date = date;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getSourceId() {
    return sourceId;
  }

  public void setSourceId(long sourceId) {
    this.sourceId = sourceId;
  }

  public long getTargetId() {
    return targetId;
  }

  public void setTargetId(long targetId) {
    this.targetId = targetId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
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
    result = 31 * result + Long.valueOf(sourceId).hashCode();
    result = 31 * result + Long.valueOf(targetId).hashCode();
    result = 31 * result + Double.valueOf(amount).hashCode();
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

    return operation.sourceId == sourceId
        && operation.targetId == targetId
        && operation.amount == amount
        && operation.date.equals(date);
  }
}
