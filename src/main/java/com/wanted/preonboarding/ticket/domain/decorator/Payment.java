package com.wanted.preonboarding.ticket.domain.decorator;

import com.wanted.preonboarding.ticket.domain.validator.ReservationPaymentDto;

import java.math.BigDecimal;

public abstract class Payment {

    public abstract BigDecimal pay(ReservationPaymentDto reservationPaymentDto);
}
