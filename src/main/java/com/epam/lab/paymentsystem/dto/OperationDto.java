package com.epam.lab.paymentsystem.dto;

public class OperationDto {
  private Long cardSrcId;
  private Long cardDstId;
  private Long amount;

  public OperationDto() {
  }

  /** Constructor with parameters for operation dto.
   * @param cardSrcId Long
   * @param cardDstId Long
   * @param amount Long
   */
  public OperationDto(Long cardSrcId, Long cardDstId, Long amount) {
    this.cardSrcId = cardSrcId;
    this.cardDstId = cardDstId;
    this.amount = amount;
  }

  public Long getCardSrcId() {
    return cardSrcId;
  }

  public void setCardSrcId(Long cardSrcId) {
    this.cardSrcId = cardSrcId;
  }

  public Long getCardDstId() {
    return cardDstId;
  }

  public void setCardDstId(Long cardDstId) {
    this.cardDstId = cardDstId;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }
}
