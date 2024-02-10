package com.wanted.preonboarding.ticketing.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CancelReservationRequest {
    @NotNull(message = "예약 Id는 필수 입력 사항입니다")
    private final Long reservationId;
    @NotNull(message = "좌석 정보 Id는 필수 입력 사항입니다")
    private final Long reservationSeatId;
}
