package com.wanted.preonboarding.layered.application.ticketing.v3.policy;

import com.wanted.preonboarding.layered.domain.dto.PerformanceDiscountPolicyInfo;
import com.wanted.preonboarding.layered.infrastructure.repository.PerformanceDiscountPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class DiscountPolicyFactory {
    private final PerformanceDiscountPolicyRepository performanceDiscountPolicyRepository;

    public DiscountPolicy create(UUID performanceId, int fixedPrice, String policyName) {
        PerformanceDiscountPolicyInfo info = PerformanceDiscountPolicyInfo.of(performanceDiscountPolicyRepository.findByPerformanceIdAndName(performanceId, policyName));
        return switch (policyName) {
            case "telecome" -> () -> info.getRate().multiply(new BigDecimal(String.valueOf(fixedPrice))).intValue(); // 비율 할인 -> 비율에 대한 정보
            case "new_member" -> () -> fixedPrice - info.getDiscountFee(); // 고정 금액 할인 -> 고정 금액 정보 ->
            default -> () -> 0;
        };
    }


}
