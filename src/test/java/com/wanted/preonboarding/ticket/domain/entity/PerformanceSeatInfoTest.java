package com.wanted.preonboarding.ticket.domain.entity;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PerformanceSeatInfoTest {

    @Test
    void 예약_인원이_찬_경우_예외_발생() {
        // given
        final PerformanceSeatInfo seatInfo = PerformanceSeatInfo.builder()
                .status(ReservationStatus.OCCUPIED)
                .build();

        // when
        // then
        assertThatThrownBy(() -> seatInfo.validate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약이_불가능한_경우_예외_발생() {
        // given
        final PerformanceSeatInfo seatInfo = PerformanceSeatInfo.builder()
                .status(ReservationStatus.DISABLED)
                .build();

        // when
        // then
        assertThatThrownBy(() -> seatInfo.validate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약이_가능하면_예외가_발생하지_않는다() {
        // given
        final PerformanceSeatInfo seatInfo = PerformanceSeatInfo.builder()
                .status(ReservationStatus.AVAILABLE)
                .build();

        // when
        // then
        assertThatNoException().isThrownBy(() -> seatInfo.validate());
    }
}
