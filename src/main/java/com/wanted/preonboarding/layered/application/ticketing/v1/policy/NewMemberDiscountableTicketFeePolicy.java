package com.wanted.preonboarding.layered.application.ticketing.v1.policy;

import com.wanted.preonboarding.layered.domain.dto.Ticket;

import java.util.Set;

public class NewMemberDiscountableTicketFeePolicy extends TicketFeePolicy {
    private final int discountAmount;

    public NewMemberDiscountableTicketFeePolicy(Set<Ticket> tickets, int d) {
        super(tickets);
        discountAmount = d;
    }

    @Override
    protected int calculateTicketFee(Ticket t) {
        int basicFee = super.calculateFee();
        return basicFee - discountAmount;
    }
}
