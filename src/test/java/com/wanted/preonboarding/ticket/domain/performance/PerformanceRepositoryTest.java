package com.wanted.preonboarding.ticket.domain.performance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.wanted.preonboarding.support.config.RepositoryTest;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveState;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@RepositoryTest
class PerformanceRepositoryTest {

    @Autowired
    PerformanceRepository performanceRepository;

    UUID performanceId;

    @BeforeEach
    void setUp() {
        List<Performance> all = performanceRepository.findAll();
        performanceId = all.get(0).getId();
    }

    @DisplayName("공연 좌석 정보를 가져올 수 있다")
    @Test
    void findSeatByIdAndInfo() {
        // given
        final int round = 1;
        final String line = "A";
        final int seat = 1;

        // when
        PerformanceSeatInfo seatInfo = performanceRepository.findSeatByIdAndInfo(performanceId, round, line, seat)
            .orElseThrow();

        // then
        assertThat(seatInfo)
            .extracting("performanceId", "round", "line", "seat", "isReserved")
            .containsExactly(performanceId, round, line, seat, ReserveState.ENABLE);
    }

    @DisplayName("공연 이름을 가져올 수 있다.")
    @Test
    void findNameById() {
        // given
        final String name = "ant";

        // when
        String findName = performanceRepository.findNameById(performanceId)
            .orElseThrow();

        // then
        assertThat(name).isEqualTo(findName);
    }
}