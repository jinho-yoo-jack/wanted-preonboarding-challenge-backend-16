package com.wanted.preonboarding.ticket.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PerformanceTest {

    @Test
    void 공연_취소() {
        // given
        final Performance performance = Performance.builder()
                .status(ReservationStatus.AVAILABLE)
                .build();

        // when
        performance.cancel();

        // then
        assertThat(performance.getStatus()).isEqualTo(ReservationStatus.DISABLED);
    }
}
