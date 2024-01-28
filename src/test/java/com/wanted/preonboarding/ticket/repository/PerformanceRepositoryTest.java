package com.wanted.preonboarding.ticket.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;

@RepositoryTest
class PerformanceRepositoryTest {

    private final PerformanceRepository performanceRepository;

    public PerformanceRepositoryTest(final PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    @Test
    void 아이디로_공연_조회() {
        // given
        final Performance performance = Performance.builder()
                .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .name("공연 이름")
                .price(10000)
                .type(PerformanceType.EXHIBITION)
                .status(ReservationStatus.AVAILABLE)
                .start_date(LocalDateTime.of(2024, 2, 1, 10, 0))
                .build();
        performanceRepository.save(performance);

        // when
        final Performance findPerformance = performanceRepository.findById(performance.getId()).get();

        // then
        assertThat(findPerformance).usingRecursiveComparison()
                .isEqualTo(performance);
    }
    
    @Test
    void 예약_가능_여부로_모든_공연_조회() {
        // given
        final Performance performance = Performance.builder()
                .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .name("공연 이름")
                .price(10000)
                .type(PerformanceType.EXHIBITION)
                .status(ReservationStatus.AVAILABLE)
                .start_date(LocalDateTime.of(2024, 2, 1, 10, 0))
                .build();
        performanceRepository.save(performance);
        final ReservationStatus status = ReservationStatus.AVAILABLE;

        // when
        final List<Performance> reservablePerformances = performanceRepository.findAllByStatus(status);

        // then
        assertThat(reservablePerformances).hasSize(1);
    }
}
