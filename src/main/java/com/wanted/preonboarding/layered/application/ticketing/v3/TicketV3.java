package com.wanted.preonboarding.layered.application.ticketing.v3;

import com.wanted.preonboarding.layered.application.ticketing.v3.policy.DiscountPolicy;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class TicketV3 {
    public final UUID performanceId;
    private final String performanceName;
    private final int round;
    private final char line;
    private final int seat;
    private int totalPrice;
    private final int fixedPrice;
    private int discountedFee;
    private Set<DiscountPolicy> appliedDiscountPolicies;

    public TicketV3 calculateTotalFee() {
        setCalculatedDiscountFee();
        totalPrice = fixedPrice - discountedFee;
        return this;
    }

    private void setCalculatedDiscountFee() {
        discountedFee = this.getAppliedDiscountPolicies().stream()
            .map(DiscountPolicy::calculateDiscountFee)
            .reduce(Integer::sum).orElse(0);
    }
}
