package com.wanted.preonboarding.ticket.controller.model.request;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationApplyRequest {
    private String userName;
    private String phoneNumber;
    private BigDecimal amountAvailable;
    private int round;
    private Long performanceId;
    private Long performanceSeatId;
}
