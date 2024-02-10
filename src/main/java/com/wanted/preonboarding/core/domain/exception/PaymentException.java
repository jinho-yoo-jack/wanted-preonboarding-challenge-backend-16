package com.wanted.preonboarding.core.domain.exception;

import org.springframework.http.HttpStatus;

public class PaymentException extends TicketException {
  public PaymentException() {
    super(HttpStatus.BAD_REQUEST);
  }

  public PaymentException(String msg) {
    super(msg, HttpStatus.BAD_REQUEST);
  }

  public PaymentException(Throwable e) {
    super(e, HttpStatus.BAD_REQUEST);
  }
}
