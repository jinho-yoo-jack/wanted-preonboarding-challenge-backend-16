package com.wanted.preonboarding.ticket.application.strategy;

import com.wanted.preonboarding.account.domain.vo.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PercentDiscountPolicy extends DiscountPolicy {

    private final BigDecimal percentAmount;

    public PercentDiscountPolicy(List<DiscountCondition> discountConditions, BigDecimal percentAmount) {
        super(discountConditions);
        this.percentAmount = percentAmount;
    }

    @Override
    protected void discount(Money originMoney) {
        BigDecimal rate = percentAmount.divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY);
        BigDecimal discountMoney = originMoney.getAmount().multiply(rate);

        originMoney.subtract(discountMoney);
    }
}
