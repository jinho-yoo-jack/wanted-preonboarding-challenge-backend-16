package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.aop.dto.BaseResDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter @Setter
@SuperBuilder
public class CreateReservationResponse {

    private UUID performanceId; //공연ID
    private int round; //회차
    private String performanceName; // 공연명
    private int gate; //좌석정보
    private char line; //좌석정보
    private int seat; //좌석정보
    private String reservationName; //이름
    private String reservationPhoneNumber; //연락처

    public static CreateReservationResponse from(Reservation reservation) {
        return CreateReservationResponse.builder()
                .performanceId(reservation.getPerformanceId())
                .round(reservation.getRound())
                .gate(reservation.getGate())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .build();
    }

    public static CreateReservationResponse of(Performance performance, Reservation reservation) {
        return CreateReservationResponse
                .builder()
                .performanceId(performance.getId())
                .performanceName(performance.getName())
                .round(reservation.getRound())
                .gate(reservation.getGate())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .build();
    }
}
