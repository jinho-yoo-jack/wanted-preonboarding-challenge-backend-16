package com.wanted.preonboarding.reservation.domain.event;

import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class ValidateReservationRequestEvent {

    private final UUID performanceId;
    private final long account;
    private final SeatInfo seatInfo;

    public static ValidateReservationRequestEvent from(ReservationRequest reservationRequest) {
        return ValidateReservationRequestEvent.builder()
                .performanceId(reservationRequest.getPerformanceId())
                .account(reservationRequest.getAccount())
                .seatInfo(SeatInfo.from(reservationRequest))
                .build();
    }
}
