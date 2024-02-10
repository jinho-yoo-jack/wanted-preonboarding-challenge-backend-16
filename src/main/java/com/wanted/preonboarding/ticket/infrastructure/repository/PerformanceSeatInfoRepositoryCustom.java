package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.request.ReserveInfoRequest;

public interface PerformanceSeatInfoRepositoryCustom {
	boolean isAvailable(ReserveInfoRequest request);
}
