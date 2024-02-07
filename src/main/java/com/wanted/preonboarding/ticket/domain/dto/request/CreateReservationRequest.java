package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Builder
public class CreateReservationRequest {
    private String reservationName; // 고객의 이름
    private String reservationPhoneNumber; // 휴대 전화
    private long amount; // 결제 가능한 금액(잔고)
    private UUID performanceId; // 예약을 원하는 공연 또는 전시회ID
    private int round; // 회차
    private int gate; // 좌석 정보
    private char line; // 좌석 정보
    private int seat; // 좌석 정보
    private String reservationStatus; // 예약; 취소;

    public void payAmount(long amount) {
        this.amount = this.amount - amount;
    }
}
