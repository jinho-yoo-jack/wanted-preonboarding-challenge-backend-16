package com.wanted.preonboarding.ticket.controller.model;


import com.wanted.preonboarding.ticket.domain.Reservation;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationApplyModel {

    private PerformanceInfoModel performanceInfo;
    private TicketHolderModel ticketHolder;

    public static ReservationApplyModel of(Reservation reservation) {
        return ReservationApplyModel.builder()
                .performanceInfo(PerformanceInfoModel.of(
                        reservation.getPerformance(),
                        reservation.getPerformanceSeat()))
                .ticketHolder(TicketHolderModel.builder()
                        .userName(reservation.getUserName())
                        .phoneNumber(reservation.getPhoneNumber())
                        .build())
                .build();
    }
}
