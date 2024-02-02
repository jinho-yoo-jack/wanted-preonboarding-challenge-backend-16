package com.wanted.preonboarding.ticket.domain.dto.discount;

import lombok.Getter;

@Getter
public enum MembershipEnum {
    GOLD(0.8),
    SILVER(0.6),
    BRONZE(0.4);

    private final double discountRate;

    private MembershipEnum(double discountRate) {
        this.discountRate = discountRate;
    }
}