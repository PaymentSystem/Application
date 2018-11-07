package com.epam.lab.paymentsystem.dto;

public class CardDto {
  private long id;
  private long accountId;
  private String userLogin;
  private String label;
  private boolean isActive;

  public CardDto() {
  }

  /**
   * Constructor with parameters for card dto.
   *
   * @param id card's id
   * @param accountId card linked to this account
   * @param userLogin card linked to this user
   * @param label card's label
   * @param isActive boolean flag
   */
  public CardDto(long id, long accountId, String userLogin, String label, boolean isActive) {
    this.id = id;
    this.accountId = accountId;
    this.userLogin = userLogin;
    this.label = label;
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

  public String getUserLogin() {
    return userLogin;
  }

  public void setUserLogin(String userLogin) {
    this.userLogin = userLogin;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
