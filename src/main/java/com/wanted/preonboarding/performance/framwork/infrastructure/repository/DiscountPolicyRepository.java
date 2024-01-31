package com.wanted.preonboarding.performance.framwork.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.discount_policy.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {

}
