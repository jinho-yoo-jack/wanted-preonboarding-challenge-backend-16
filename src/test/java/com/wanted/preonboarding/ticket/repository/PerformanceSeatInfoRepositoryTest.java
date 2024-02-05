package com.wanted.preonboarding.ticket.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;

@RepositoryTest
class PerformanceSeatInfoRepositoryTest {

    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final PerformanceRepository performanceRepository;

    public PerformanceSeatInfoRepositoryTest(final PerformanceSeatInfoRepository performanceSeatInfoRepository,
                                             final PerformanceRepository performanceRepository) {
        this.performanceSeatInfoRepository = performanceSeatInfoRepository;
        this.performanceRepository = performanceRepository;
    }

    @Test
    void 공연_아이디와_좌석_정보의_값으로_좌석_정보_조회() {
        // given
        final Performance performance = createPerformance();
        final PerformanceSeatInfo performanceSeatInfo = PerformanceSeatInfo.builder()
                .round(1)
                .line('A')
                .seat(1)
                .status(ReservationStatus.AVAILABLE)
                .performance(performance)
                .build();
        performanceSeatInfoRepository.save(performanceSeatInfo);

        // when
        final PerformanceSeatInfo findPerformanceSeatInfo = performanceSeatInfoRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                performance.getId(), performanceSeatInfo.getRound(),
                performanceSeatInfo.getLine(), performanceSeatInfo.getSeat()).get();

        // then
        assertThat(findPerformanceSeatInfo).usingRecursiveComparison()
                .isEqualTo(performanceSeatInfo);
    }

    private Performance createPerformance() {
        final Performance performance = Performance.builder()
                .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .name("공연 이름")
                .type(PerformanceType.CONCERT)
                .price(10000)
                .status(ReservationStatus.AVAILABLE)
                .start_date(LocalDateTime.of(2024, 2, 1, 10, 0))
                .build();
        return performanceRepository.save(performance);
    }
}
