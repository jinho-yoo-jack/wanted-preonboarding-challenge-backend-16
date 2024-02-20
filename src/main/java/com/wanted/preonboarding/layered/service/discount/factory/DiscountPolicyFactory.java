package com.wanted.preonboarding.layered.service.discount.factory;

import com.wanted.preonboarding.domain.entity.PerformanceDiscountPolicy;
import com.wanted.preonboarding.layered.repository.DiscountPolicyRepository;
import com.wanted.preonboarding.layered.service.discount.DiscountPolicy;
import com.wanted.preonboarding.layered.service.discount.FeeDiscountPolicy;
import com.wanted.preonboarding.layered.service.discount.RateDiscountPolicy;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountPolicyFactory {
  private final DiscountPolicyRepository repository;

  public DiscountPolicy getPolicy(UUID performanceId, Long fixedPrice, String policyName) {
    PerformanceDiscountPolicy policy = this.repository.findByPerformanceIdAndName(performanceId, policyName);
    return switch (policyName) {
      case "telecom" -> new RateDiscountPolicy(policy.getRate(), fixedPrice);
      case "new" -> new FeeDiscountPolicy(policy.getFee(), fixedPrice);
      default -> () -> 0L;
    };
  }
}
