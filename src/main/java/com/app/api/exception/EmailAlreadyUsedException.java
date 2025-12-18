package com.app.api.exception;

public class EmailAreadyUsedException extends RuntimeException {
  public EmailAreadyUsedException(String message) {
    super(message);
  }
}
