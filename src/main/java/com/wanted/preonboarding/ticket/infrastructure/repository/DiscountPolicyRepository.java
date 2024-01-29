package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {

}
