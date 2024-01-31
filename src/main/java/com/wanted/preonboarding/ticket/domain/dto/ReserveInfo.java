package com.wanted.preonboarding.ticket.domain.dto;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder

@AllArgsConstructor
@NoArgsConstructor

//예약 신청
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId;
    private String reservationName;
    private String reservationPhoneNumber;
    private String reservationStatus; //예약 상태: 요청 Requested 대기 Pending 승인 Approved 거절 Rejected
    private long amount; //결제 가능한 금액 (잔고)
    private int round;
    private char line;
    private int seat;

    public void setReserveInfo(String status){
        this.reservationStatus = status ;

    }


}
