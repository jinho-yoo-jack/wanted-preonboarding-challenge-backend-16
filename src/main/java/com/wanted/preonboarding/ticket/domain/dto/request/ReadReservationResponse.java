package com.wanted.preonboarding.ticket.domain.dto.request;

import com.wanted.preonboarding.ticket.domain.dto.response.CreateReservationResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
public class ReadReservationResponse {

    private UUID performanceId; //공연ID
    private int round; //회차
    private String performanceName; // 공연명
    private int gate; //좌석정보
    private char line; //좌석정보
    private int seat; //좌석정보
    private String reservationName; //이름
    private String reservationPhoneNumber; //연락처


    public static ReadReservationResponse of(Performance performance, Reservation reservation) {
        return ReadReservationResponse
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
