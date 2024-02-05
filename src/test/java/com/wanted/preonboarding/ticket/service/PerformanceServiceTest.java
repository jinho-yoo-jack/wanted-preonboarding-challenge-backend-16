package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.ticket.controller.model.PerformanceSelectModel;
import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.repository.PerformanceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Transactional
class PerformanceServiceTest {

    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private PerformanceRepository performanceRepository;

    @DisplayName("공연/전시 목록 조회 시 정상적으로 조회된다.")
    @Test
    void getPerformances() {
        // given
        Performance performance1 = createPerformance("레베카");
        Performance performance2 = createPerformance("아이다");
        performanceRepository.saveAll(List.of(performance1, performance2));

        // when
        boolean isReserve = true;
        List<PerformanceSelectModel> result = performanceService.getPerformances(isReserve);

        // then
        assertThat(result).hasSize(2)
                .extracting("title", "round")
                .containsExactlyInAnyOrder(
                        tuple("레베카", 1),
                        tuple("아이다", 1)
                );
    }

    @DisplayName("공연/전시 상세 조회 시 정상적으로 조회된다.")
    @Test
    void getPerformance() {
        // given
        Performance performance1 = createPerformance("레베카");
        Performance save = performanceRepository.save(performance1);

        // when
        boolean isReserve = true;
        PerformanceSelectModel result = performanceService.getPerformance(save.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("레베카");
    }

    private Performance createPerformance(String title) {
        return Performance.builder()
                .title(title)
                .price(100000)
                .round(1)
                .startDate(LocalDate.now())
                .isReserve(true)
                .build();
    }
}
