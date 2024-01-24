package com.wanted.preonboarding.reservation.domain.event;

import com.wanted.preonboarding.common.model.SeatInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class SeatReservedEvent {

    private final SeatInfo seatInfo;

    private final UUID performanceId;

    public static SeatReservedEvent of(final SeatInfo seatInfo, final UUID performanceId) {
        return new SeatReservedEvent(seatInfo, performanceId);
    }
}
