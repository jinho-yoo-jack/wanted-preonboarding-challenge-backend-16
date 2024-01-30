package com.wanted.preonboarding.ticket.dto.request.discount;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record DiscountInfo(
    LocalDateTime requestTime,
    String performanceId,
    String phoneNumber,
    String name,
    int age
) {

    public static DiscountInfo of(PaymentInfo paymentInfo) {
        return DiscountInfo.builder()
            .requestTime(paymentInfo.requestTime())
            .performanceId(paymentInfo.performanceId())
            .phoneNumber(paymentInfo.phoneNumber())
            .name(paymentInfo.name())
            .age(paymentInfo.age())
            .build();
    }
}
