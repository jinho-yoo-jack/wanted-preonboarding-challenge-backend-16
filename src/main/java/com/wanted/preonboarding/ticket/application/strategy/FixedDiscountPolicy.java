package com.wanted.preonboarding.ticket.application.strategy;

import com.wanted.preonboarding.account.domain.vo.Money;

import java.util.List;

public class FixedDiscountPolicy extends DiscountPolicy {

    private final Money amount;

    public FixedDiscountPolicy(List<DiscountCondition> discountConditions, Money amount) {
        super(discountConditions);
        this.amount = amount;
    }

    @Override
    protected void discount(Money originMoney) {
        originMoney.subtract(amount.getAmount());
    }
}
