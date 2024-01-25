package com.wanted.preonboarding.ticketing.domain.dto.email;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class EmailPerformance {
    private final UUID id;
    private final String name;
    private final int round;
    private final LocalDateTime startDate;
}
