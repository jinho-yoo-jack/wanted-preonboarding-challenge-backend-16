package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.support.PerformanceSeatFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PerformanceSeatInfoTest {

    @DisplayName("공연의 좌석이 현재 예약이 가능하면 true를 반환한다.")
    @Test
    void performanceSeatWithPossibleReservation() {
        // given
        PerformanceSeatInfo performanceSeatInfo = PerformanceSeatFactory.create();

        // when
        boolean possibleReserve = performanceSeatInfo.isPossibleReserve();

        // then
        assertThat(possibleReserve).isTrue();
    }

    @DisplayName("공연의 좌석이 현재 예약이 불가능하면 false를 반환한다.")
    @Test
    void performanceSeatWithImpossibleReservation() {
        // given
        PerformanceSeatInfo performanceSeatInfo = PerformanceSeatFactory.create();

        PerformanceSeatInfo changedPerformanceSeatInfo = PerformanceSeatFactory.
                changeReservationStatus(performanceSeatInfo, PerformanceSeatInfo.DISABLE);

        // when
        boolean possibleReserve = changedPerformanceSeatInfo.isPossibleReserve();

        // then
        assertThat(possibleReserve).isFalse();
    }
}