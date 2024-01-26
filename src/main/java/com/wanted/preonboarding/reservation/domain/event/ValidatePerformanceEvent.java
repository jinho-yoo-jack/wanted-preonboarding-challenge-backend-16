package com.wanted.preonboarding.reservation.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class ValidatePerformanceEvent {

    private final UUID performanceId;
}
