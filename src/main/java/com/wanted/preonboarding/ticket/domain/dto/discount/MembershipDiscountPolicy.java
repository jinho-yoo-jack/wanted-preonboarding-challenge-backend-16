package com.wanted.preonboarding.ticket.domain.dto.discount;

public class MembershipDiscountPolicy implements DiscountPolicy {
    @Override
    public int getDiscountPolicy(int charge, String membershipGrade) {
        MembershipEnum membershipEnum = MembershipEnum.valueOf(membershipGrade.toUpperCase());

        switch (membershipEnum) {
            case GOLD:
                return (int) (charge - (charge * MembershipEnum.GOLD.getDiscountRate()));
            case SILVER:
                return (int) (charge - (charge * MembershipEnum.SILVER.getDiscountRate()));
            case BRONZE:
                return (int) (charge - (charge * MembershipEnum.BRONZE.getDiscountRate()));
            default:
                throw new IllegalArgumentException("Invalid membership grade: " + membershipGrade);
        }
    }
}
