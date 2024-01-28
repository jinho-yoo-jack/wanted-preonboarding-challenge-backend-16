package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.application.exception.NotReservedStateException;
import com.wanted.preonboarding.ticket.support.PerformanceSeatFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("해당 공연의 좌석을 취소하면 공연 좌석을 다시 예약 할 수 있는 상태가 된다.")
    @Test
    void cancelSeat() {
        // given
        PerformanceSeatInfo performanceSeatInfo = PerformanceSeatFactory.create();
        PerformanceSeatInfo changedPerformanceSeatInfo = PerformanceSeatFactory.
                changeReservationStatus(performanceSeatInfo, PerformanceSeatInfo.DISABLE);

        // when
        changedPerformanceSeatInfo.cancel();

        // then
        assertThat(changedPerformanceSeatInfo.getIsReserve()).isEqualTo(PerformanceSeatInfo.ENABLE);
    }

    @DisplayName("해당 공연의 좌석이 예약 가능 상태라면 예외를 반환한다.")
    @Test
    void cancelSeatWithEnableStatus() {
        // given
        PerformanceSeatInfo performanceSeatInfo = PerformanceSeatFactory.create();

        // when & then
        assertThatThrownBy(performanceSeatInfo::cancel)
                .isInstanceOf(NotReservedStateException.class)
                .hasMessage(String.format(PerformanceSeatInfo.NOT_RESERVED_MESSAGE_FORMAT, performanceSeatInfo.getId()));
    }
}