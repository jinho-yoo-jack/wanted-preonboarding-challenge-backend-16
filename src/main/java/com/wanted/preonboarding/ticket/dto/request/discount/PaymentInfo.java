package com.wanted.preonboarding.ticket.dto.request.discount;

import java.time.LocalDateTime;

public record PaymentInfo(
    LocalDateTime requestTime,
    int performanceId,
    String name,
    String phoneNumber,
    int age

) {

}
