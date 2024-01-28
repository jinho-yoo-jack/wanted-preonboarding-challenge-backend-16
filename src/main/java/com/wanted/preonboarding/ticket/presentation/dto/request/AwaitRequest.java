package com.wanted.preonboarding.ticket.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AwaitRequest(
        @NotNull(message = "userId는 필수 값입니다.")
        Long userId
) {

}
