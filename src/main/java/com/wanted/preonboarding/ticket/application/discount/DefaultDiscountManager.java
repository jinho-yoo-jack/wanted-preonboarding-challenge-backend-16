package com.wanted.preonboarding.ticket.application.discount;

import com.wanted.preonboarding.ticket.application.discount.policy.DiscountPolicy;
import com.wanted.preonboarding.ticket.dto.request.discount.DiscountInfo;
import com.wanted.preonboarding.ticket.dto.request.discount.PaymentInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultDiscountManager implements DiscountManager {

    private final List<DiscountPolicy> discountPolicies;

    @Override
    public double applyDiscount(final PaymentInfo paymentInfo) {
        DiscountInfo discountInfo = DiscountInfo.of(paymentInfo);
        int originPrice = paymentInfo.price();

        for (DiscountPolicy policy : discountPolicies) {
            originPrice -= (int) policy.getDiscountAmount(discountInfo, originPrice);
        }
        return originPrice;
    }

}
