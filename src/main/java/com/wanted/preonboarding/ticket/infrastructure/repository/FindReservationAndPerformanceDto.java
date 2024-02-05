package com.wanted.preonboarding.ticket.infrastructure.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FindReservationAndPerformanceDto {
    //  예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
    private int round;
    private String performanceName;
    private char line;
    private int seat;
    private UUID performanceId;
    private String reservationName;
    private String reservationPhoneNumber;
}
