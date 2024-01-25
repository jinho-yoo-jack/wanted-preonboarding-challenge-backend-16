package com.wanted.preonboarding.ticket.application.strategy;

import com.wanted.preonboarding.account.vo.Money;

public interface DiscountCondition {

    boolean isSatisfied(DiscountConditionInfo info);
}
