package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Builder

@AllArgsConstructor

//예약 신청
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId;
    private String reservationName;
    private String reservationPhoneNumber;
    private ReservationStatus reservationStatus; //예약 상태: 요청 Requested 대기 Pending 승인 Approved 거절 Rejected
    private long amount; //결제 가능한 금액 (잔고)
    private int round;
    private char line;
    private int seat;


    public ReserveInfo() {
        this.reservationStatus = ReservationStatus.PENDING;
    }

    public void setReservationStatusToREJECTED() {
        this.reservationStatus = ReservationStatus.REJECTED;
    }

    public void setReservationStatusToAPPROVED() {
        this.reservationStatus = ReservationStatus.APPROVED;
    }

    public boolean CalculateAmount(int price) {
        if (this.amount < price) {
            setReservationStatusToREJECTED();
            return false;

        } else {
            this.amount = this.amount - price;
            return true;
        }


    }
}
