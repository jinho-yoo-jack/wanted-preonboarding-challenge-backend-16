package com.wanted.preonboarding.ticket.infrastructure.decorator;

import com.wanted.preonboarding.ticket.domain.decorator.Payment;
import com.wanted.preonboarding.ticket.domain.validator.ReservationPaymentDto;

import java.math.BigDecimal;

public class PerformancePayment extends Payment {//실제 결제금액
    @Override
    public BigDecimal pay(ReservationPaymentDto reservationPaymentDto) {
        return new BigDecimal(String.valueOf(reservationPaymentDto.getPerformancePrice()));
    }
}
