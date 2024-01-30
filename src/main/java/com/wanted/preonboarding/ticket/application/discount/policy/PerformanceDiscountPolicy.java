package com.wanted.preonboarding.ticket.application.discount.policy;

import static com.wanted.preonboarding.ticket.domain.discount.model.DiscountType.PERCENT;

import com.wanted.preonboarding.ticket.domain.discount.Discount;
import com.wanted.preonboarding.ticket.domain.discount.DiscountRepository;
import com.wanted.preonboarding.ticket.dto.request.discount.DiscountInfo;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/*
* 공연에 적용된 할인 (DISCOUNT 테이블 조회)
* */
@RequiredArgsConstructor
public class PerformanceDiscountPolicy implements DiscountPolicy {

    private final DiscountRepository discountRepository;

    @Override
    public double getDiscountAmount(final DiscountInfo discountInfo, final int originPrice) {
        UUID performanceId = UUID.fromString(discountInfo.performanceId());
        List<Discount> discounts = discountRepository.findDiscountsByPerformanceId(performanceId, discountInfo.requestTime());

        // 할인이 적용된 금액 - 총 할인 금액
        int discountedPrice = originPrice;
        double totalDiscountAmount = 0;

        // 할인 금액 구하기
        for (Discount discount : discounts) {
            double amount = applyDiscount(discount, discountedPrice);
            discountedPrice -= (int) amount;
            totalDiscountAmount += amount;
        }

        return totalDiscountAmount;
    }

    private double applyDiscount(final Discount discount, int originPrice) {
        if (discount.getType() == null) return originPrice;
        if (discount.getType() == PERCENT) return applyPercentDiscount(originPrice, discount.getAmount());
        return discount.getAmount();
    }

    // % 할인
    private double applyPercentDiscount(final int originPrice, final int percent) {
        return originPrice * ((double) percent / 100);
    }

}
