package com.wanted.preonboarding.ticket.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class PerformanceSeatInfo {
	private char line;
	private int seat;
	public static PerformanceSeatInfo create(char line, int seat){
	        return new PerformanceSeatInfo(line,seat);
	    }
}
