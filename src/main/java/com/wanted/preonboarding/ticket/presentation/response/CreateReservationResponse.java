package com.wanted.preonboarding.ticket.presentation.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Builder
public class CreateReservationResponse {
    // 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
    private int round;
    private String performanceName;
    private String seatInformation;
    private UUID performanceId;
    private String reservationName;
    private String reservationPhoneNumber;

    public static CreateReservationResponse of(int round, String performanceName, char line, int seat, UUID performanceId, String reservationName, String reservationPhoneNumber) {
        String seatInformation = new StringBuilder()
                .append(line)
                .append("열 ")
                .append(seat)
                .append("번")
                .toString();

        return builder()
                .round(round)
                .performanceName(performanceName)
                .seatInformation(seatInformation)
                .performanceId(performanceId)
                .reservationName(reservationName)
                .reservationPhoneNumber(reservationPhoneNumber)
                .build();
    }
}
