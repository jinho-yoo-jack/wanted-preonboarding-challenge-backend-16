package com.wanted.preonboarding.layered.repository;

import com.wanted.preonboarding.domain.entity.PerformanceDiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountPolicyRepository extends JpaRepository<PerformanceDiscountPolicy, Integer> {

}
