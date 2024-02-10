package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.dto.request.ReserveInfoRequest;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public void calculateAmount(int price) {
        long result = this.amount - price;
        if (result < 0) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        this.amount = result;
    }

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
