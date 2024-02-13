package com.wanted.preonboarding.layered.application.ticketing.v1.policy;

import com.wanted.preonboarding.layered.domain.dto.Ticket;

import java.math.BigDecimal;
import java.util.Set;

public class TelecomeNewMemberDiscountableTicketFeePolicy extends TelecomeDiscountableTicketFeePolicy {
    private final int discountAmount;

    public TelecomeNewMemberDiscountableTicketFeePolicy(Set<Ticket> tickets, int d, BigDecimal r) {
        super(tickets, r);
        discountAmount = d;
    }

    @Override
    protected int calculateTicketFee(Ticket t) {
        int basicFee = super.calculateFee();
        return basicFee - discountAmount;
    }
}
