package com.wanted.preonboarding.ticket.domain.decorator;

import com.wanted.preonboarding.ticket.domain.code.DiscountType;
import com.wanted.preonboarding.ticket.domain.validator.ReservationPaymentDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class DiscountPaymentDecorator extends Payment {//결제 확장 - 할인 (할인 외 다른게 추가될 수 있음))

    Payment payment;

    public DiscountPaymentDecorator(Payment payment) {
        this.payment = payment;
    }

    public abstract DiscountType getDiscountType();

    public BigDecimal pay(ReservationPaymentDto reservationPaymentDto) {
        BigDecimal discountRate = new BigDecimal(String.valueOf(reservationPaymentDto.getDiscountTypeAndRateMap().get(getDiscountType())));
        BigDecimal performancePrice = getPerformancePrice(reservationPaymentDto);
        return this.payment.pay(reservationPaymentDto).subtract(getDiscountAmount(performancePrice, discountRate));
    }

    protected BigDecimal getPerformancePrice(ReservationPaymentDto reservationPaymentDto) {
        return new BigDecimal(String.valueOf(reservationPaymentDto.getPerformancePrice()));
    }

    protected BigDecimal getDiscountAmount(BigDecimal performancePrice, BigDecimal discountRate) {
        return performancePrice.divide(new BigDecimal("100"), RoundingMode.HALF_UP).multiply(new BigDecimal(String.valueOf(discountRate)));
    }
}
