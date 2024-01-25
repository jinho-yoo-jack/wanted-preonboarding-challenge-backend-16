package com.wanted.preonboarding.reservation.domain.event;

import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ReservationCanceledEvent {

    private final SeatInfo seatInfo;
    private final Performance performance;

    public static ReservationCanceledEvent of(final SeatInfo seatInfo,final Performance performance) {
        return new ReservationCanceledEvent(seatInfo, performance);
    }

    public UUID getPerformanceId() {
        return this.performance.getId();
    }
}
