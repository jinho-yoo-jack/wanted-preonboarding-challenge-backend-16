package com.wanted.preonboarding.ticket.presentation.response;

import com.wanted.preonboarding.ticket.infrastructure.repository.FindReservationAndPerformanceDto;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ReadReservationResponse {
    // 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
    private int round;
    private String performanceName;
    private String seatInformation;
    private UUID performanceId;
    private String reservationName;
    private String reservationPhoneNumber;

    public static ReadReservationResponse of(FindReservationAndPerformanceDto findReservationAndPerformanceDto) {
        String seatInformation = new StringBuilder()
                .append(findReservationAndPerformanceDto.getLine())
                .append("열 ")
                .append(findReservationAndPerformanceDto.getSeat())
                .append("번")
                .toString();

        return builder()
                .round(findReservationAndPerformanceDto.getRound())
                .performanceName(findReservationAndPerformanceDto.getPerformanceName())
                .seatInformation(seatInformation)
                .performanceId(findReservationAndPerformanceDto.getPerformanceId())
                .reservationName(findReservationAndPerformanceDto.getReservationName())
                .reservationPhoneNumber(findReservationAndPerformanceDto.getReservationPhoneNumber())
                .build();

    }
}
