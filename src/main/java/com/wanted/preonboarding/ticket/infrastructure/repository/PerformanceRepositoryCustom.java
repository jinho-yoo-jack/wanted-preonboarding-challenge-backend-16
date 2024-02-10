package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.request.PerformanceListRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceListResponse;
import java.util.List;

public interface PerformanceRepositoryCustom {
    List<PerformanceListResponse> findPerformanceAll(PerformanceListRequest request);
}
