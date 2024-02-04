package com.wanted.preonboarding.ticket.dto.request.discount;

import com.wanted.preonboarding.ticket.dto.request.reservation.ReservationRequest;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PaymentInfo(
    int price,
    LocalDateTime requestTime,
    String performanceId,
    String name,
    String phoneNumber,
    int age

) {

    public static PaymentInfo of(int price, ReservationRequest reservationRequest, LocalDateTime requestTime) {
        return PaymentInfo.builder()
            .price(price)
            .requestTime(requestTime)
            .performanceId(reservationRequest.performanceId())
            .name(reservationRequest.name())
            .phoneNumber(reservationRequest.phone())
            .age(reservationRequest.age())
            .build();
    }

}
