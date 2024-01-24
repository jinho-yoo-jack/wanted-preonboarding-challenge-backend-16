package com.wanted.preonboarding.reservation.domain.event;

import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
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

    public static SeatReservedEvent from(final ReservationRequest reservationRequest) {
        return SeatReservedEvent.of(SeatInfo.from(reservationRequest),
                reservationRequest.getPerformanceId());
    }
}
