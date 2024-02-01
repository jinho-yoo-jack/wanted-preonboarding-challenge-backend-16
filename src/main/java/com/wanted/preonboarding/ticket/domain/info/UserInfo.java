package com.wanted.preonboarding.ticket.domain.info;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserInfo {
	@Column(nullable = false, name = "name")
	@NotBlank
	private String reservationName;
	@Column(nullable = false, name = "phone_number")
	@NotBlank
	private String reservationPhoneNumber;

	public static UserInfo of(String name, String phoneNumber) {
		return new UserInfo(name, phoneNumber);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserInfo userInfo = (UserInfo)o;
		return Objects.equals(reservationName, userInfo.reservationName) && Objects.equals(
			reservationPhoneNumber, userInfo.reservationPhoneNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(reservationName, reservationPhoneNumber);
	}
}
