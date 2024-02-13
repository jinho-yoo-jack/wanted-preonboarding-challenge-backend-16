package com.wanted.preonboarding.layered.application.ticketing.v2.policy;

public class BasicFeePolicy implements FeePolicy {
    private final String grade;

    public BasicFeePolicy(String g) {
        grade = g;
    }

    @Override
    public int calculateFee(int price) {
        return price;
    }


}
