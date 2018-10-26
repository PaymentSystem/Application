package com.epam.lab.paymentsystem.exception;

public class LoginAlreadyExistsException extends Exception {

  public LoginAlreadyExistsException() {
    super();
  }

  public LoginAlreadyExistsException(String message) {
    super(message);
  }
}
