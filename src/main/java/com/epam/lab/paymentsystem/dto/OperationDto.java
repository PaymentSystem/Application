package com.epam.lab.paymentsystem.dto;

public class OperationDto {
  private long cardSrcId;
  private long cardDstId;
  private long amount;
  private String numberSrcCard;
  private String numberDstCard;

  public OperationDto() {
  }

  /**
   * Constructor with parameters for operation dto.
   *
   * @param cardSrcId Long
   * @param cardDstId Long
   * @param amount    Long
   * @param numberSrcCard String
   * @param numberDstCard String
   *
   */
  public OperationDto(Long cardSrcId, long cardDstId, long amount,
                      String numberSrcCard, String numberDstCard) {
    this.cardSrcId = cardSrcId;
    this.cardDstId = cardDstId;
    this.amount = amount;
    this.numberDstCard = numberDstCard;
    this.numberSrcCard = numberSrcCard;
  }

  public long getCardSrcId() {
    return cardSrcId;
  }

  public void setCardSrcId(long cardSrcId) {
    this.cardSrcId = cardSrcId;
  }

  public long getCardDstId() {
    return cardDstId;
  }

  public void setCardDstId(long cardDstId) {
    this.cardDstId = cardDstId;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
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
}
