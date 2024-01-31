package com.wanted.preonboarding.reservation.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class NamePhone {

	@Column(nullable = false)
	private String name;
	@Column(nullable = false, name = "phone_number")
	private String phoneNumber;

	public static NamePhone create(String name, String phoneNumber) {
		return new NamePhone(name, phoneNumber);
	}
}
