package com.wanted.preonboarding.reservation.domain.event;

import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class SeatReservedEvent {

    private final SeatInfo seatInfo;

    private final UUID performanceId;

    public static SeatReservedEvent of(final SeatInfo seatInfo, final UUID performanceId) {
        return SeatReservedEvent.builder()
                .seatInfo(seatInfo)
                .performanceId(performanceId)
                .build();
    }

    public static SeatReservedEvent from(final ReservationRequest reservationRequest) {
        return SeatReservedEvent.of(SeatInfo.from(reservationRequest),
                reservationRequest.getPerformanceId());
    }
}
