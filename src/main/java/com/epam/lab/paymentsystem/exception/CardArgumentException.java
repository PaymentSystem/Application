package com.epam.lab.paymentsystem.exception;

public class CardArgumentException extends RuntimeException {
  public CardArgumentException() {
    super();
  }

  public CardArgumentException(String message) {
    super(message);
  }
}
