package com.wanted.preonboarding.ticket.application.performance;

import com.wanted.preonboarding.ticket.dto.response.page.PageResponse;
import com.wanted.preonboarding.ticket.dto.response.performance.PerformanceInfo;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface PerformanceService {
    // Performance ID 조회
    PerformanceInfo findPerformance(UUID performanceId);

    /*
    * 페이지 단위 공연 조회
    * reserveState - 예약 가능 여부 (enable, disable)
    * */
    PageResponse<PerformanceInfo> findPerformanceAll(String reserveState, Pageable pageable);
}
