package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.request.ReserveInfoRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.WaitReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.WaitReservationResponse;

public interface PerformanceSeatInfoRepositoryCustom {
	boolean isAvailable(ReserveInfoRequest request);
	WaitReservationResponse isAvailable(WaitReservationRequest request);
}
