package com.wanted.preonboarding.performance.repository;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.constant.PerformanceType;
import com.wanted.preonboarding.performance.domain.constant.ReserveStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@DisplayName("[DB 접근] Performance repository 테스트")
@DataJpaTest
public class PerformanceRepositoryTest {
    @Autowired
    private PerformanceRepository performanceRepository;

    private Performance createPerformance() {
        Performance performance = Performance.builder()
                .name("test")
                .price(30000)
                .round(2)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .type(PerformanceType.EXHIBITION)
                .reserveStatus(ReserveStatus.ENABLE)
                .build();
        return performanceRepository.save(performance);
    }
    
    @Test
    public void givenPageable_whenSearchingPerformance_thenEmptyPage() {
        //given
        createPerformance();
        Pageable pageable = Pageable.ofSize(10);
        //when
        Page<Performance> page = performanceRepository.findAll(pageable);
        //then
        Assertions.assertThat(page).isNotEmpty();
        Assertions.assertThat(page.getTotalPages()).isGreaterThan(0);
    }

    @DisplayName("uuid로 검색을 하면 performance가 반환된다.")
    @Test
    public void givenUUID_whenSearchingPerformance_thenReturnsPerformance() {
        //given
        UUID uuid = createPerformance().getId();
        //when
        Optional<Performance> performance = performanceRepository.findById(uuid);
        //then
        Assertions.assertThat(performance).isNotEmpty();
        Assertions.assertThat(performance.get().getId()).isEqualTo(uuid);
    }

    @DisplayName("performance를 저장하면 performance가 반환된다.")
    @Test
    public void givenPerformance_whenSavePerformance_thenReturnsPerformance() {
        //given
        Performance performance = Performance.builder()
                .name("인생의 회전목마")
                .price(30000)
                .round(2)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .type(PerformanceType.EXHIBITION)
                .reserveStatus(ReserveStatus.ENABLE)
                .build();
        //when
        Performance savedPerformance = performanceRepository.save(performance);
        //then
        Assertions.assertThat(savedPerformance).isNotNull();
        Assertions.assertThat(savedPerformance).isEqualTo(performance);
    }

    @DisplayName("UUID로 삭제를 한 후 다시 검색을 하면 빈 Optional이 반환된다.")
    @Test
    public void givenUUID_whenDeleteAndSearchPerformance_thenEmptyOptional() {
        //given
        UUID uuid = createPerformance().getId();
        //when
        performanceRepository.deleteById(uuid);
        Optional<Performance> performance = performanceRepository.findById(uuid);
        //then
        Assertions.assertThat(performance).isEmpty();
    }

}
