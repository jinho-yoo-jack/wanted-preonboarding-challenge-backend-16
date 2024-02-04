package com.wanted.preonboarding.ticket.domain.performance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PerformanceSearchRepository {

    // 모든 공연 & 전시 정보 조회
    Page<Performance> findAll(String reserveState, Pageable pageable);
}
