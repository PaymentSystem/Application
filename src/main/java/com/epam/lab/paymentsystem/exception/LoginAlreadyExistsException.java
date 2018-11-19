package com.epam.lab.paymentsystem.exception;

public class LoginAlreadyExistsException extends RuntimeException {

  public LoginAlreadyExistsException() {
    super();
  }

  public LoginAlreadyExistsException(String message) {
    super(message);
  }
}
