package com.wanted.preonboarding.domain.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends TicketException {
  public UserNotFoundException() {
    super(HttpStatus.NOT_FOUND);
  }

  public UserNotFoundException(String msg) {
    super(msg, HttpStatus.NOT_FOUND);
  }

  public UserNotFoundException(String msg, Throwable e) {
    super(msg, e);
    this.status = HttpStatus.NOT_FOUND;
  }

  public UserNotFoundException(String msg, HttpStatus status) {
    super(msg, status);
  }
}
