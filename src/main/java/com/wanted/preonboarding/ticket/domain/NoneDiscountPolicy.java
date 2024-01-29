package com.wanted.preonboarding.ticket.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue(value="amount")
@PrimaryKeyJoinColumn(name = "id")
public class NoneDiscountPolicy extends DiscountPolicy{

	@Override
	public int getDiscountAmount(int price) {
		return 0;
	}
}
