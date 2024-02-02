package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId;
    private String reservationName; // 예약자 이름
    private String phoneNumber; // 예약자 핸드폰 번호
    private String reservationStatus; // 예약; 취소;
    private long balanceAmount;
    private int round;
    private char line;
    private int seat;

    public void updateBalanceAmount(long balanceAmount){
        this.balanceAmount = balanceAmount;
    }
}
