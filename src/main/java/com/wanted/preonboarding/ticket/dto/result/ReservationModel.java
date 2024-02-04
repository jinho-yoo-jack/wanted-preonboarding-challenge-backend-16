package com.wanted.preonboarding.ticket.dto.result;

import com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ReservationModel (
    UUID performanceId,
    String performanceName,
    LocalDateTime startDate,
    PerformanceType type,
    int gate,
    int round,
    String line,
    int seat,
    String name,
    String phoneNumber,
    LocalDateTime reserveDate

) {

}
