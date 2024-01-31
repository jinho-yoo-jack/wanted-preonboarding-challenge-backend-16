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
public class SeatInfo {

	@Column(nullable = false)
	private char line;
	@Column(nullable = false)
	private int seat;
	public static SeatInfo create(char line, int seat){
	        return new SeatInfo(line,seat);
	    }
}
