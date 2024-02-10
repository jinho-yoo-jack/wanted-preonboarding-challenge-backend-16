package com.wanted.preonboarding.ticket.application.policy.discount;

public interface DiscountPolicy {
	Long calculateAmount(int performanceAmount, Long reservationAmount);
}
