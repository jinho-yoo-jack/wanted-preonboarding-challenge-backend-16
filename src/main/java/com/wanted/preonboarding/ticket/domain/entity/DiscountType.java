package com.wanted.preonboarding.ticket.domain.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DiscountType {
	NONE("할인없음"),
	AMOUNT("금액할인");

	private final String name;
}
