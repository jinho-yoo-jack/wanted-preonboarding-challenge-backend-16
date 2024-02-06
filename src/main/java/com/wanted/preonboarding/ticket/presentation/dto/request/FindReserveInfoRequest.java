package com.wanted.preonboarding.ticket.presentation.dto.request;

import com.wanted.preonboarding.ticket.application.dto.request.FindReserveServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record FindReserveInfoRequest(
        @NotBlank(message = "예약자 이름은 필수입니다.")
        String reservationName,

        @NotBlank(message = "휴대폰 번호는 필수입니다.")
        String reservationPhoneNumber
) {

        public FindReserveServiceRequest toService() {
                return FindReserveServiceRequest.builder()
                        .reservationName(reservationName)
                        .reservationPhoneNumber(reservationPhoneNumber)
                        .build();
        }
}
