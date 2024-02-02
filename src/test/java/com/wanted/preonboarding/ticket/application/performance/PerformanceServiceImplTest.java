package com.wanted.preonboarding.ticket.application.performance;

import static com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType.CONCERT;
import static com.wanted.preonboarding.ticket.domain.performance.model.ReserveState.ENABLE;
import static com.wanted.preonboarding.ticket.domain.performance.model.ReserveState.RESERVED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveState;
import com.wanted.preonboarding.ticket.dto.response.page.PageResponse;
import com.wanted.preonboarding.ticket.dto.response.performance.PerformanceInfo;
import com.wanted.preonboarding.ticket.exception.notfound.PerformanceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class PerformanceServiceImplTest {

    @Autowired
    PerformanceService performanceService;

    @Autowired
    PerformanceRepository performanceRepository;

    @BeforeEach
    void setUp() {
        performanceRepository.deleteAllInBatch();
    }

    @DisplayName("특정 공연 정보를 조회할 수 있다.")
    @Test
    void findPerformance_success() {
        // given
        String performanceName = "Reveca";
        UUID performanceId = savePerformance(performanceName, ENABLE);

        // when
        PerformanceInfo findInfo = performanceService.findPerformance(performanceId);

        // then
        assertThat(findInfo.name()).isEqualTo(performanceName);
    }

    @DisplayName("잘못된 ID로 조회하면 예외가 발생한다.")
    @Test
    void findPerformance_fail_invalid_id() {
        // given
        String performanceName = "Reveca";
        UUID performanceId = savePerformance(performanceName, ENABLE);
        final UUID unknownId = UUID.randomUUID();

        // when & then
        assertThatThrownBy(() -> performanceService.findPerformance(unknownId))
            .isExactlyInstanceOf(PerformanceNotFoundException.class);
    }

    @DisplayName("전체 공연 정보를 조회할 수 있다.")
    @Test
    void findPerformanceAll_success() {
        // given
        for (int i = 1; i <= 10; i++) {
            savePerformance("Hola" + i, ENABLE);
        }
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final String reserveState = "enable";

        // when
        PageResponse<PerformanceInfo> performances = performanceService.findPerformanceAll(reserveState, pageRequest);

        // then
        List<PerformanceInfo> contents = performances.contents();
        assertThat(contents).hasSize(10)
            .extracting("name")
            .contains("Hola1", "Hola2", "Hola3", "Hola4", "Hola5", "Hola6", "Hola7", "Hola8", "Hola9", "Hola10");
    }

    @DisplayName("예약 상태에 맞는 공연 정보를 조회한다.")
    @Test
    void findPerformanceAll_success_with_reserveState() {
        // given
        for (int i = 1; i <= 10; i++) {
            ReserveState state = ENABLE;
            if (i % 2 == 0) state = RESERVED;
            savePerformance("Hola" + i, state);
        }
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final String reserveState = "disable";

        // when
        PageResponse<PerformanceInfo> performances = performanceService.findPerformanceAll(reserveState, pageRequest);

        // then
        List<PerformanceInfo> contents = performances.contents();
        assertThat(contents).hasSize(5)
            .extracting("name")
            .contains("Hola2", "Hola4", "Hola6", "Hola8", "Hola10");
    }

    @DisplayName("예약 상태 정보를 입력하지 않으면 예약 상태와 관계없이 모든 데이터가 조회된다.")
    @Test
    void findPerformanceAll_success_without_reserveState() {
        // given
        for (int i = 1; i <= 10; i++) {
            ReserveState state = ENABLE;
            if (i % 2 == 0) state = RESERVED;
            savePerformance("Hola" + i, state);
        }
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final String reserveState = "";

        // when
        PageResponse<PerformanceInfo> performances = performanceService.findPerformanceAll(reserveState, pageRequest);

        // then
        List<PerformanceInfo> contents = performances.contents();
        assertThat(contents).hasSize(10)
            .extracting("name")
            .contains("Hola1", "Hola2", "Hola3", "Hola4", "Hola5", "Hola6", "Hola7", "Hola8", "Hola9", "Hola10");
    }

    @DisplayName("예약 상태가 disable 또는 enable 이 아니면 예외가 발생한다.")
    @Test
    void findPerformanceAll_fail_invalid_state() {
        // given
        for (int i = 1; i <= 10; i++) {
            ReserveState state = ENABLE;
            if (i % 2 == 0) state = RESERVED;
            savePerformance("Hola" + i, state);
        }
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final String invalidReserveState = "invalid";

        // when & then
        assertThatThrownBy(() -> performanceService.findPerformanceAll(invalidReserveState, pageRequest))
            .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    private UUID savePerformance(final String name, final ReserveState state) {
        LocalDateTime startDate = LocalDateTime.of(2024, 12, 31, 19,0);
        final Performance performance = Performance.builder()
            .name(name)
            .price(100_000)
            .round(1)
            .type(CONCERT)
            .startDate(startDate)
            .isReserve(state)
            .build();
        return performanceRepository.save(performance).getId();
    }
}