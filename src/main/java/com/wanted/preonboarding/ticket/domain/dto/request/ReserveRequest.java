package com.wanted.preonboarding.ticket.domain.dto.request;

import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;

import java.util.UUID;

/**
 * Represents a reservation request made by a customer.
 */
public class ReserveRequest {
    private String name; //고객 이름
    private String phoneNumber; //휴대 전화
    private Integer amount; //결제 가능한 금액
    private Long performanceId; // 공연 & 전시회 아이디
    private Integer round; // 회차
    private Character line; //라인
    private Integer seat; //좌석 정보

    public ReserveInfo toDto() {
        return ReserveInfo.builder()
                .performanceInfo(PerformanceInfo.builder()
                        .performanceId(performanceId)
                        .build())
                .reservationName(name)
                .reservationPhoneNumber(phoneNumber)
                .reservationStatus("reserve")
                .amount(amount)
                .round(round)
                .line(line)
                .seat(seat)
                .build();
    }

}
