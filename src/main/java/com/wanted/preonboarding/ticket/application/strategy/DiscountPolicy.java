package com.wanted.preonboarding.ticket.application.strategy;

import com.wanted.preonboarding.account.domain.vo.Money;

import java.util.List;

public abstract class DiscountPolicy {

    private final List<DiscountCondition> discountConditions;

    public DiscountPolicy(List<DiscountCondition> discountConditions) {
        this.discountConditions = discountConditions;
    }

    public Money calculateDiscountedAmount(Money originMoney, DiscountConditionInfo info) {
        for (DiscountCondition condition : discountConditions) {
            if (condition.isSatisfied(info)) {
                discount(originMoney);
            }
        }

        return originMoney;
    }

    protected abstract void discount(Money originMoney);
}
