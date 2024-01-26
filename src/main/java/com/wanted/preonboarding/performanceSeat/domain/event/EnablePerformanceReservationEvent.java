package com.wanted.preonboarding.performanceSeat.domain.event;

import com.wanted.preonboarding.common.model.PerformanceId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class EnablePerformanceReservationEvent {

    private final PerformanceId performanceId;

    public UUID getPerformanceIdValue() {
        return this.getPerformanceIdValue();
    }
}
