package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ReserveInfo {
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
}
