package com.wanted.preonboarding.ticket.domain.performance;

import static com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType.CONCERT;
import static com.wanted.preonboarding.ticket.domain.performance.model.ReserveState.ENABLE;
import static com.wanted.preonboarding.ticket.domain.performance.model.ReserveState.RESERVED;
import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.support.config.RepositoryTest;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveState;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

@RepositoryTest
class PerformanceSearchRepositoryImplTest {

    @Autowired
    PerformanceRepository performanceRepository;

    @DisplayName("전체 공연 & 전시 정보가 조회된다.")
    @Test
    void findAll_success() {
        // given
        int day = 1;
        for (int i = 1; i <= 5; i++) {
            LocalDateTime startDate = LocalDateTime.of(2023, 12, day, 19, 00);
            savePerformance(startDate, "Hola" + i, ENABLE);
            day++;
        }
        final String reserveState = "enable";
        final Sort sort = Sort.by(Order.desc("startDate"));
        final PageRequest pageRequest = PageRequest.of(0, 5, sort);

        // when
        Page<Performance> findPerformance = performanceRepository.findAll(reserveState, pageRequest);

        // then
        List<Performance> content = findPerformance.getContent();
        assertThat(content).hasSize(5)
            .extracting("name")
            .containsExactlyInAnyOrder("Hola1", "Hola2", "Hola3", "Hola4", "Hola5");
    }

    @DisplayName("예매 가능 여부가 없으면 전체 데이터를 불러온다.")
    @Test
    void findAll_success_state_null() {
        // given
        int day = 1;
        for (int i = 1; i <= 5; i++) {
            ReserveState state = ENABLE;
            if (i % 2 == 0) state = RESERVED;

            LocalDateTime startDate = LocalDateTime.of(2023, 12, day, 19, 00);
            savePerformance(startDate, "Hola" + i, state);
            day++;
        }
        final Sort sort = Sort.by(Order.desc("startDate"));
        final PageRequest pageRequest = PageRequest.of(0, 5, sort);

        final String reserveState = "";

        // when
        Page<Performance> findPerformance = performanceRepository.findAll(reserveState, pageRequest);

        // then
        assertThat(findPerformance.getContent()).hasSize(5);
    }

    private void savePerformance(final LocalDateTime startDate, final String name, ReserveState state) {
        final Performance performance = Performance.builder()
            .name(name)
            .price(100_000)
            .round(1)
            .type(CONCERT)
            .startDate(startDate)
            .isReserve(state)
            .build();
        performanceRepository.save(performance);
    }

}