package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceFindResponse;
import com.wanted.preonboarding.ticket.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PerformanceServiceTest {
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private PerformanceRepository performanceRepository;
    private final String DISABLE = "disable";
    private final String ENABLE = "enable";

    @Test
    @DisplayName("공연 전체 조회 성공")
    public void findAllPerformanceSuccess() {
        List<PerformanceFindResponse> performances = performanceService.findPerformances(DISABLE);
        Assertions.assertThat(performances).isNotNull();
    }

    @Test
    @DisplayName("공연 전체 조회 실패 (해당하는 공연이 없을 경우)")
    public void findAllPerformanceFail() {
        // 빈 List로 반환할지 PerformanceNotFoundException으로 반환할지 다시 확인
        Assertions.assertThatThrownBy(() -> performanceService.findPerformances(ENABLE))
                .isInstanceOf(PerformanceNotFoundException.class);
    }
}
