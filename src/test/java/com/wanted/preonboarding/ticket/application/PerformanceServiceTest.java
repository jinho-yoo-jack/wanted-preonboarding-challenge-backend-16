package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.support.PerformanceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class PerformanceServiceTest {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private PerformanceService performanceService;

    @DisplayName("하나의 공연을 조회 할 수 있다.")
    @Test
    void findOne() {
        // given
        Performance performance = PerformanceFactory.create(null);
        Performance savedPerformance = performanceRepository.save(performance);

        // when
        PerformanceResponse response = performanceService.findOne(savedPerformance.getId());

        // then
        assertThat(response).isNotNull();
    }

    @DisplayName("예약이 가능한 공연을 조회 할 수 있다.")
    @Test
    void findAvailablePerformances() {
        // when
        List<PerformanceResponse> performances = performanceService.findPerformances(Performance.ENABLE);

        // then
        assertThat(performances).isNotEmpty();
        assertThat(performances).extracting("isReserve").containsExactlyInAnyOrder(Performance.ENABLE);
    }

    @DisplayName("예약이 불가능한 공연을 조회 할 수 있다.")
    @Test
    void findUnavailablePerformances() {
        // when
        List<PerformanceResponse> performances = performanceService.findPerformances(Performance.DISABLE);

        // then
        assertThat(performances).isNotEmpty();
        assertThat(performances).extracting("isReserve").containsExactlyInAnyOrder(Performance.DISABLE);
    }
}
