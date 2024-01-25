package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId; // 공연 및 전시 ID

    private String reservationName; // 예약자 이름

    private String reservationPhoneNumber; // 예약자 폰 번호

    private String reservationStatus; // 예약; 취소; 예약 상태

    private long amount; // 예약금

    private int round; // 회차
    private int gate; // 입장 게이트
    private char line; // 줄
    private int seat; // 좌석

    public Reservation toEntity() {
        return Reservation.builder()
                .performanceId(performanceId)
                .name(reservationName)
                .phoneNumber(reservationPhoneNumber)
                .round(round)
                .gate(gate)
                .line(line)
                .seat(seat)
                .build();
    }
}
