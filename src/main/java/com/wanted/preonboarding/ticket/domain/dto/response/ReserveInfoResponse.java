package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.dto.request.ReserveInfoRequest;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReserveInfoResponse {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId;
    private String reservationName;
    private String reservationPhoneNumber;
    private String reservationStatus; // 예약; 취소;
    private long amount;
    private int round;
    private char line;
    private int seat;

    public static ReserveInfoResponse of(ReserveInfoRequest request) {
        return ReserveInfoResponse.builder()
            .round(request.getRound())
            .seat(request.getSeat())
            .line(request.getLine())
            .performanceId(request.getPerformanceId())
            .reservationName(request.getReservationName())
            .reservationPhoneNumber(request.getReservationPhoneNumber())
            .build();
    }
}
