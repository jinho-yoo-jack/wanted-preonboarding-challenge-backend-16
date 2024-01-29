package com.wanted.preonboarding.ticket.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.PrimaryKeyJoinColumn;

@DiscriminatorValue(value="amount")
@PrimaryKeyJoinColumn(name = "id")
public class PercentDiscountPolicy extends DiscountPolicy {

	double percent;

	@Override
	public int getDiscountAmount(int price) {

		return (int) (price * percent);
	}
}
