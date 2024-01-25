package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private PerformanceInfo performanceInfo;
    private String reservationName;
    private String reservationPhoneNumber;
    private String reservationStatus; // 예약; 취소;
    private long amount;
    private int round;
    private char line;
    private int seat;
}
