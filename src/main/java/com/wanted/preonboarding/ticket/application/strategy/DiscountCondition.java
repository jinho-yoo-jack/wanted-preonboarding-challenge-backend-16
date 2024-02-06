package com.wanted.preonboarding.ticket.application.strategy;

public interface DiscountCondition {

    boolean isSatisfied(DiscountConditionInfo info);
}
