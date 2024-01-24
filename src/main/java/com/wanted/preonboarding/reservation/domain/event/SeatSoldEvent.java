package com.wanted.preonboarding.reservation.domain.event;

import com.wanted.preonboarding.common.model.SeatInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SeatSoldEvent {

    private final SeatInfo seatInfo;

    public static SeatSoldEvent of(final SeatInfo seatInfo) {
        return new SeatSoldEvent(seatInfo);
    }
}
