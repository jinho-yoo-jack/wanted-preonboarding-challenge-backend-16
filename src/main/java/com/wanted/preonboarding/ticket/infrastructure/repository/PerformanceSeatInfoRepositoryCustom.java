package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;

public interface PerformanceSeatInfoRepositoryCustom {
	boolean isAvailable(ReserveInfo request);
}
