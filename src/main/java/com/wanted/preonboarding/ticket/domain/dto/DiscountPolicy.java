package com.wanted.preonboarding.ticket.domain.dto;

public enum DiscountPolicy {
    TEN_PERCENT(0.1),
    THIRTY_PERCENT(0.3),
    DEFAULT(0.0); // 기본 할인 정책

    private final double discountRate;

    DiscountPolicy(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public static DiscountPolicy valueOfDiscountRate(String discountPolicyString) {
        return switch (discountPolicyString) {
            case "0.1" -> TEN_PERCENT;
            case "0.3" -> THIRTY_PERCENT;
            default -> DEFAULT;
        };
    }

}