package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class ReservePerformanceInfo {
    // 예매가 완료된 공연의 정보(공연ID, 회차, 공연명, 좌석정보)
    private UUID performanceId;
    private int performanceRound;
    private String performanceName;
    private int reservationGate;
    private char reservationLine;
    private int reservationSeat;

    // 예매자 정보(이름, 연락처)
    private String reservationName;
    private String reservationPhone;

    public static ReservePerformanceInfo of(Reservation reservation, Performance performance) {
        return ReservePerformanceInfo.builder()
                .performanceId(performance.getId())
                .performanceRound(performance.getRound())
                .performanceName(performance.getName())
                .reservationGate(reservation.getGate())
                .reservationLine(reservation.getLine())
                .reservationSeat(reservation.getSeat())
                .reservationName(reservation.getName())
                .reservationPhone(reservation.getPhoneNumber())
                .build();
    }
}
