package com.wanted.preonboarding.layered.application.ticketing.v2.policy;

public class NewMember extends AdditionDiscountPolicy {
    public NewMember(FeePolicy n) {
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
