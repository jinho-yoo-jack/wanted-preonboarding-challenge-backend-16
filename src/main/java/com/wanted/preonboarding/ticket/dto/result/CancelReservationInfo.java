package com.wanted.preonboarding.ticket.dto.result;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CancelReservationInfo(
    UUID performanceId,
    String name,
    int round,
    String line,
    int seat,
    LocalDateTime startDate,
    int price
) {

}
