package com.wanted.preonboarding.ticket.domain.decorator;

import com.wanted.preonboarding.ticket.domain.validator.ReservationPaymentDto;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public abstract class DirectPaymentDecorator extends Payment {//직접 결제

    Payment payment;

    public DirectPaymentDecorator(Payment payment) {
        this.payment = payment;
    }

    public abstract BigDecimal pay(ReservationPaymentDto reservationPaymentDto);
}
