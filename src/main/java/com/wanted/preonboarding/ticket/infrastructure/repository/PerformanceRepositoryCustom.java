package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.application.dto.PerformanceDto;
import com.wanted.preonboarding.ticket.application.dto.PerformanceSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PerformanceRepositoryCustom {
    Page<PerformanceDto> searchPage(PerformanceSearchCondition condition, Pageable pageable);
}
