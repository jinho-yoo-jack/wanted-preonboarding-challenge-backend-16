package com.wanted.preonboarding.ticket.application.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateReserveServiceRequest(
        Long userId,
        Long performanceSeatInfoId
) {

}
