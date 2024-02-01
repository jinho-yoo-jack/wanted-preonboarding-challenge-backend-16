package com.wanted.preonboarding.ticket.domain.info;

import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.interfaces.controller.dto.ReservationRequest;

@Getter
@Setter
@Builder
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId;
    private String performanceName;
    @Embedded
    private UserInfo userInfo;
    private ReservationStatus reservationStatus; // 예약; 취소;
    private double amount;
    private int round;
    @Embedded
    private SeatInfo seatInfo;
    private int age;
    private int rate;

    public static ReserveInfo of(ReservationRequest request, double price) {
        return ReserveInfo.builder()
            .performanceId(UUID.fromString(request.performanceId()))
            .performanceName(request.performanceName())
            .userInfo(UserInfo.of(request.reservationName(), request.reservationPhoneNumber()))
            .round(request.round())
            .seatInfo(SeatInfo.of(request.line(), request.seat()))
            .age(request.age())
            .amount(request.amount() - price)
            .reservationStatus(ReservationStatus.RESERVE)
            .rate((int)price)
            .build();
    }

    public static ReserveInfo from(Reservation reservation, String performanceName) {
        return ReserveInfo.builder()
            .performanceId(reservation.getPerformanceId())
            .performanceName(performanceName)
            .userInfo(UserInfo.of(reservation.getUserInfo().getReservationName(), reservation.getUserInfo().getReservationPhoneNumber()))
            .round(reservation.getRound())
            .seatInfo(SeatInfo.of(reservation.getSeatInfo().getLine(), reservation.getSeatInfo().getSeat()))
            .reservationStatus(ReservationStatus.RESERVE)
            .build();
    }
}
