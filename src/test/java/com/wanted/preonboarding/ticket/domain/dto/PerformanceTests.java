package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.support.PerformanceFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
public class PerformanceTests {

    @Test
    public void enumTest() {
        int code = 2;
        String typeName = Arrays.stream(PerformanceType.values()).filter(value -> value.getCategory() == code)
            .findFirst()
            .orElse(PerformanceType.NONE)
            .name();
        assertEquals("EXHIBITION", typeName);
    }

    @DisplayName("공연이 예약이 가능하면 true를 반환한다.")
    @Test
    void performanceWithPossibleReservation() {
        // given
        UUID performanceId = UUID.randomUUID();
        Performance performance = PerformanceFactory.create(performanceId);
        Performance changedPerformance = PerformanceFactory.changeReservationState(performance, Performance.ENABLE);

        // when
        boolean possibleReserve = changedPerformance.isPossibleReserve();

        // then
        assertThat(possibleReserve).isTrue();
    }

    @DisplayName("공연이 예약이 불가능하면 false를 반환한다.")
    @Test
    void performanceWithImpossibleReservation() {
        // given
        UUID performanceId = UUID.randomUUID();
        Performance performance = PerformanceFactory.create(performanceId);

        // when
        boolean possibleReserve = performance.isPossibleReserve();

        // then
        assertThat(possibleReserve).isFalse();
    }
}
