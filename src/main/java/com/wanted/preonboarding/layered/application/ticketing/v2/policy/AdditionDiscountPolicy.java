package com.wanted.preonboarding.layered.application.ticketing.v2.policy;

public abstract class AdditionDiscountPolicy implements FeePolicy {
    private final FeePolicy next;

    public AdditionDiscountPolicy(FeePolicy n) {
        next = n;
    }

    @Override
    public int calculateFee(int price) {
        int discountedAmount = calculateDiscountAmount(price);
        return afterCalculated(next.calculateFee(discountedAmount));
    }

    abstract int calculateDiscountAmount(int price);
    abstract int afterCalculated(int fee);

}
