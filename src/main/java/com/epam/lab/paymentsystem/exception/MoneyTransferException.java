package com.epam.lab.paymentsystem.exception;

public class MoneyTransferException extends RuntimeException {
  public MoneyTransferException() {
    super();
  }

  public MoneyTransferException(String message) {
    super(message);
  }
}
