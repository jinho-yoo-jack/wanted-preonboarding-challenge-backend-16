package com.wanted.preonboarding.ticket.application.discount;

import com.wanted.preonboarding.ticket.application.discount.policy.DiscountPolicy;
import com.wanted.preonboarding.ticket.dto.request.discount.PaymentInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DefaultDiscountManager implements DiscountManager {

    private final List<DiscountPolicy> discountPolicies;

    @Override
    public double applyDiscount(PaymentInfo paymentInfo) {
        return paymentInfo.price();
    }
}
