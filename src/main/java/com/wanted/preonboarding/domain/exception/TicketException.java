package com.wanted.preonboarding.domain.exception;

import org.springframework.http.HttpStatus;

public class TicketException extends RuntimeException {
  protected HttpStatus status;

  public TicketException() {
    super();
  }

  public TicketException(String msg) {
    super(msg);
  }

  public TicketException(Throwable e) {
    super(e);
  }

  public TicketException(Throwable e, HttpStatus s) {
    super(e);
    this.status = s;
  }

  public TicketException(String msg, Throwable e) {
    super(msg, e);
  }

  public TicketException(HttpStatus status) {
    super();
    this.status = status;
  }

  public TicketException(String msg, HttpStatus status) {
    super(msg);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return this.status;
  }
}
