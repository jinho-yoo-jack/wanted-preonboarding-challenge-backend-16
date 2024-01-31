package com.wanted.preonboarding.performance.domain.discount_policy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(value="amount")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AmountDiscountPolicy extends DiscountPolicy{

	private int amount;

	public AmountDiscountPolicy(int amount) {
		this.amount = amount;
	}

	@Override
	public int getDiscountAmount(int price) {
		return amount;
	}
}
