package com.wanted.preonboarding.ticket.controller.model.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationApplyRequest {
    private String userName;
    private String phoneNumber;
    private double amountAvailable;
    private int round;
    private Long performanceId;
    private Long performanceSeatId;
}
