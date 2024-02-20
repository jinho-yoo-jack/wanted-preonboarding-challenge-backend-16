package com.wanted.preonboarding.layered.repository;

import com.wanted.preonboarding.domain.entity.PerformanceDiscountPolicy;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountPolicyRepository extends JpaRepository<PerformanceDiscountPolicy, Integer> {
  PerformanceDiscountPolicy findByPerformanceIdAndName(UUID id, String policyName);
}
