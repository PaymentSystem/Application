package com.epam.lab.paymentsystem.exception;

public class AccountArgumentException extends RuntimeException {
  public AccountArgumentException() {
    super();
  }

  public AccountArgumentException(String message) {
    super(message);
  }
}
