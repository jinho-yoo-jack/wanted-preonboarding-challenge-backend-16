package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.support.PerformanceFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class PerformanceServiceTest {

    @Autowired
    private PerformanceRepository performanceRepository;

    @DisplayName("하나의 공연을 조회 할 수 있다.")
    @Test
    void findOne() {
        // given
        Performance performance = PerformanceFactory.create(null);
        Performance savedPerformance = performanceRepository.save(performance);

        // when
        Optional<Performance> findPerformance = performanceRepository.findById(savedPerformance.getId());

        // then
        Assertions.assertThat(findPerformance).isNotEmpty();
    }
}
