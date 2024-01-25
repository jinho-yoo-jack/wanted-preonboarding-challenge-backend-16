package com.wanted.preonboarding.performanceSeat.domain.event;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class EnablePerformanceReservationEvent {

    private final Performance performance;
}
