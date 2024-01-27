package com.wanted.preonboarding.ticket.presentation.dto.request;

import com.wanted.preonboarding.ticket.application.dto.request.CreateReserveServiceRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record CreateReserveInfoRequest(
        @NotNull(message = "예약 시 유저 Id는 필수 입니다.")
        Long userId,

        @NotNull(message = "예약 시 공연 좌석 정보는 필수 입니다.")
        Long performanceSeatInfoId

) {

    public CreateReserveServiceRequest toService() {
        return CreateReserveServiceRequest.builder()
                .userId(userId)
                .performanceSeatInfoId(performanceSeatInfoId)
                .build();
    }
}
