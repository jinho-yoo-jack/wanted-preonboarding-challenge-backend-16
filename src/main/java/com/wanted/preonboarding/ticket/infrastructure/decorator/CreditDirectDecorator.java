package com.wanted.preonboarding.ticket.infrastructure.decorator;

import com.wanted.preonboarding.ticket.domain.decorator.DirectPaymentDecorator;
import com.wanted.preonboarding.ticket.domain.decorator.Payment;
import com.wanted.preonboarding.ticket.domain.validator.ReservationPaymentDto;

import java.math.BigDecimal;

public class CreditDirectDecorator extends DirectPaymentDecorator {
    public CreditDirectDecorator(Payment payment) {
        super(payment);
    }

    @Override
    public BigDecimal pay(ReservationPaymentDto reservationPaymentDto) {
        return super.getPayment().pay(reservationPaymentDto).subtract(reservationPaymentDto.getDirectPaymentPrice());
    }
}
