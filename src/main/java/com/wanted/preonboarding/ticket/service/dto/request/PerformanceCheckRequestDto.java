package com.wanted.preonboarding.ticket.service.dto.request;

import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;

public record PerformanceCheckRequestDto(ReservationStatus status) {
}
