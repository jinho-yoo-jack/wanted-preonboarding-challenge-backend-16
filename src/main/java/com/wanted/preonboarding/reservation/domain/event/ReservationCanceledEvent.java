package com.wanted.preonboarding.reservation.domain.event;

import com.wanted.preonboarding.common.model.PerformanceId;
import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ReservationCanceledEvent {

    private final SeatInfo seatInfo;
    private final PerformanceId performanceId;

    public static ReservationCanceledEvent of(final SeatInfo seatInfo,final PerformanceId performanceId) {
        return new ReservationCanceledEvent(seatInfo, performanceId);
    }

    public UUID getPerformanceIdValue() {
        return this.performanceId.getValue();
    }
}
