package com.wanted.preonboarding.layered.application.ticketing.v1.policy;

import com.wanted.preonboarding.layered.domain.dto.Ticket;

import java.math.BigDecimal;
import java.util.Set;

public class TelecomeDiscountableTicketFeePolicy extends TicketFeePolicy {
    private final BigDecimal discountRate;

    public TelecomeDiscountableTicketFeePolicy(Set<Ticket> tickets, BigDecimal rate) {
        super(tickets);
        discountRate = rate;
    }

    @Override
    protected int calculateTicketFee(Ticket t) {
        int basicFee = super.calculateFee(); // 부모 클래스의 코드를 재사용하는 부분
        basicFee = basicFee - discountRate.multiply(new BigDecimal(String.valueOf(basicFee))).intValue();
        return basicFee;
    }
}
