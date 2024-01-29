package com.wanted.preonboarding.ticketing.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReadReservationRequest {
    @NotBlank(message = "예약자명은 필수 입력 사항입니다")
    private final String reservationName;
    @NotBlank(message = "핸드폰 번호는 필수 입력 사항입니다")
    private final String phoneNumber;
}
