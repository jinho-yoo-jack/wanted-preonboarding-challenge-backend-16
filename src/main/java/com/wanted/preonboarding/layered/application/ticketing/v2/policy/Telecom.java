package com.wanted.preonboarding.layered.application.ticketing.v2.policy;

public class Telecom extends AdditionDiscountPolicy {
    public Telecom(FeePolicy n) {
        super(n);
    }

    @Override
    int calculateDiscountAmount(int price) {
        return 0;
    }

    @Override
    int afterCalculated(int fee) {
        return fee;
    }
}
