package com.wanted.preonboarding.ticket.application.strategy;

import java.util.List;

public class RoundDiscountCondition implements DiscountCondition {
    
    private final List<Integer> roundDiscounts;

    public RoundDiscountCondition(List<Integer> roundDiscounts) {
        this.roundDiscounts = roundDiscounts;
    }

    @Override
    public boolean isSatisfied(DiscountConditionInfo info) {
        for (int i : roundDiscounts) {
            if (isRoundApplicable(info, i)) {
                return true;
            }
        }

        return false;
    }

    private boolean isRoundApplicable(DiscountConditionInfo info, int i) {
        return info.getRound() == i;
    }
}
