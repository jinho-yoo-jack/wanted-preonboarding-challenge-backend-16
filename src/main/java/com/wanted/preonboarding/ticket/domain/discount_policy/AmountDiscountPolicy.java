package com.wanted.preonboarding.ticket.domain.discount_policy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue(value="amount")
@PrimaryKeyJoinColumn(name = "id")
public class AmountDiscountPolicy extends DiscountPolicy{

	int amount;

	@Override
	public int getDiscountAmount(int price) {
		return amount;
	}
}
