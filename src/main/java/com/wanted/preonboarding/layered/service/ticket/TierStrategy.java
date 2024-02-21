package com.wanted.preonboarding.layered.service.ticket;

@FunctionalInterface
public interface TierStrategy {
  Long getTierFee(Long fixedFee);
}
