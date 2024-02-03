package com.wanted.preonboarding.ticket.domain.info;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SeatInfo {
	@Column(nullable = false)
	private char line;
	@Column(nullable = false)
	private int seat;

	public static SeatInfo of(char line, int seat) {
		return new SeatInfo(line, seat);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SeatInfo seatInfo = (SeatInfo)o;
		return line == seatInfo.line && seat == seatInfo.seat;
	}

	@Override
	public int hashCode() {
		return Objects.hash(line, seat);
	}
}
