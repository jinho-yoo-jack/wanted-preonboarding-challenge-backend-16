package com.wanted.preonboarding.layered.service.ticket;

import com.wanted.preonboarding.layered.service.ticket.discount.DiscountPolicy;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Ticket {
  private final UUID    performanceId;
  private final String  performanceName;
  private final int     round;
  private final String  line;
  private final int     seat;
  private Long          totalPrice;
  private final Long    fixedPrice;
  private Long          discountedFee;
  private Set<DiscountPolicy> appliedDiscountPolicies;

  public Ticket calc() {
    this.calculateDiscountFee();
    this.totalPrice = this.fixedPrice - this.discountedFee;
    return this;
  }

  private void calculateDiscountFee() {
    this.discountedFee = this.appliedDiscountPolicies.stream()
        .map(DiscountPolicy::calcFee)
        .reduce(Long::sum).orElse(0L);
  }
}
