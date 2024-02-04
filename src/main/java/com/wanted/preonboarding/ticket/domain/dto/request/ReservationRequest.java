package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationRequest {
    private String performanceId;
    private ReserverInfoRequest reserverInfoRequest;
    private PaymentRequest paymentRequest;
    private SeatRequest seatRequest;
    private boolean isCancelAlarm;
}