package com.wanted.preonboarding.core.domain.exception;

import org.springframework.http.HttpStatus;

public class ReserveConflictException extends TicketException {
  public ReserveConflictException() {
    super(HttpStatus.CONFLICT);
  }
  public ReserveConflictException(Throwable e) {
    super(e, HttpStatus.CONFLICT);
  }
  public ReserveConflictException(String msg, Throwable e) {
    super(msg, e);
    this.status = HttpStatus.CONFLICT;
  }
}
