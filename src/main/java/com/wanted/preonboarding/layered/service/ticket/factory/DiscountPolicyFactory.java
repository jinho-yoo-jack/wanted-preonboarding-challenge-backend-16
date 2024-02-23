package com.wanted.preonboarding.layered.service.ticket.factory;

import com.wanted.preonboarding.domain.entity.PerformanceDiscountPolicy;
import com.wanted.preonboarding.layered.repository.DiscountPolicyRepository;
import com.wanted.preonboarding.layered.service.ticket.discount.DiscountPolicy;
import java.math.BigDecimal;
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
      case "telecom" -> () -> policy.getRate().multiply(new BigDecimal(fixedPrice)).longValue();  // return
      case "new" -> () -> Long.valueOf(policy.getFee());
      default -> () -> 0L;
    };
  }
}
