package com.wanted.preonboarding.domain.exception;

import org.springframework.http.HttpStatus;

public class SeatNotFoundException extends TicketException {
  public SeatNotFoundException() {
    super(HttpStatus.BAD_REQUEST);
  }

  public SeatNotFoundException(String msg) {
    super(msg, HttpStatus.BAD_REQUEST);
  }

  public SeatNotFoundException(String msg, Throwable e) {
    super(msg, e);
    this.status = HttpStatus.BAD_REQUEST;
  }

  public SeatNotFoundException(String msg, HttpStatus status) {
    super(msg, status);
  }
}
