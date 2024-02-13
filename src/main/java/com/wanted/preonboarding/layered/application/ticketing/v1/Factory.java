package com.wanted.preonboarding.layered.application.ticketing.v1;

import com.wanted.preonboarding.layered.application.ticketing.v1.policy.NewMemberDiscountableTicketFeePolicy;
import com.wanted.preonboarding.layered.application.ticketing.v1.policy.TelecomeDiscountableTicketFeePolicy;
import com.wanted.preonboarding.layered.application.ticketing.v1.policy.TicketFeePolicy;
import com.wanted.preonboarding.layered.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.layered.infrastructure.repository.PerformanceDiscountPolicyRepository;
import com.wanted.preonboarding.layered.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.layered.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Factory {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PerformanceDiscountPolicyRepository performanceDiscountPolicyRepository;

    public TicketSeller create(List<String> appliedPolicies, PerformanceInfo performanceInfo) {
        return new TicketSeller(performanceRepository, reservationRepository, (TicketFeePolicy) getAppliedPolicies(appliedPolicies, performanceInfo));
    }

    private TicketFeePolicy getAppliedPolicies(List<String> appliedPolicies, PerformanceInfo performanceInfo) {
        for (String policyName : appliedPolicies) {
            BigDecimal discountRate = performanceDiscountPolicyRepository.findByPerformanceIdAndName(performanceInfo.getPerformanceId(), policyName).getRate();
            int discountFee = performanceDiscountPolicyRepository.findByPerformanceIdAndName(performanceInfo.getPerformanceId(), policyName).getDiscountFee();
            switch (policyName) {
                case "telecom":
                    return new TelecomeDiscountableTicketFeePolicy(new HashSet<>(), discountRate);
                case "new_member":
                    return new NewMemberDiscountableTicketFeePolicy(new HashSet<>(), discountFee);
            }
        }
        return null;
    }


}
