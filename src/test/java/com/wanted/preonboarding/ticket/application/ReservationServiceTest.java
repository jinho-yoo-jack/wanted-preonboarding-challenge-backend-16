package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.request.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationFindRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationCreateResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationFindResponse;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.exception.*;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    private final String successPerformanceId = "2a4d95dc-c77e-11ee-88ff-0242ac130002";
    private final String failedPerformanceId = "2a4d95dc-c77e-11ee-88ff-0242ac130004";

    @Test
    @DisplayName("예약 성공")
    public void reserveSuccess() {
        // given
        ReservationCreateRequest request = ReservationCreateRequest.builder()
                .performanceId(UUID.fromString(successPerformanceId))
                .reservationName("JH")
                .reservationPhoneNumber("010-1111-2222")
                .balance(200000)
                .round(1)
                .seat(4)
                .line('A')
                .build();
        // when
        ReservationCreateResponse reserve = reservationService.reserve(request);
        Reservation findReservation = reservationRepository.findById(reserve.getId()).get();

        //then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(findReservation.getPerformance().getId()).isEqualTo(reserve.getPerformanceId());
        softAssertions.assertThat(findReservation.getName()).isEqualTo(reserve.getReservationName());
        softAssertions.assertThat(findReservation.getPhoneNumber()).isEqualTo(reserve.getReservationPhoneNumber());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("예약 실패 (잘못된 공연 ID)")
    public void reserveFailByPerformanceId() {
        // given
        ReservationCreateRequest request = ReservationCreateRequest.builder()
                .performanceId(UUID.fromString(failedPerformanceId))
                .reservationName("JH")
                .reservationPhoneNumber("010-1111-2222")
                .balance(200000)
                .round(1)
                .seat(4)
                .line('A')
                .build();
        // expected
        Assertions.assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(PerformanceNotFoundException.class);
    }

    @Test
    @DisplayName("예약 실패 (잘못된 좌석 정보)")
    public void reserveFailByWrongSeatInfo() {
        // given
        ReservationCreateRequest request = ReservationCreateRequest.builder()
                .performanceId(UUID.fromString(successPerformanceId))
                .reservationName("JH")
                .reservationPhoneNumber("010-1111-2222")
                .balance(200000)
                .round(1)
                .seat(4)
                .line('B')
                .build();
        // expected
        Assertions.assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(PerformanceSeatInfoNotFound.class);
    }

    @Test
    @DisplayName("예약 실패 (이미 예약된 좌석)")
    public void reserveFailByAlreadyReservedSeat() {
        // given
        ReservationCreateRequest request = ReservationCreateRequest.builder()
                .performanceId(UUID.fromString(successPerformanceId))
                .reservationName("JH")
                .reservationPhoneNumber("010-1111-2222")
                .balance(200000)
                .round(1)
                .seat(2)
                .line('A')
                .build();
        // expected
        Assertions.assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(SeatAlreadyReservedException.class);
    }

    @Test
    @DisplayName("예약 실패 (잔고 부족)")
    public void reserveFailByNotEnoughBalance() {
        // given
        ReservationCreateRequest request = ReservationCreateRequest.builder()
                .performanceId(UUID.fromString(successPerformanceId))
                .reservationName("JH")
                .reservationPhoneNumber("010-1111-2222")
                .balance(50000)
                .round(1)
                .seat(4)
                .line('A')
                .build();
        // expected
        Assertions.assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(NotEnoughBalanceException.class);
    }

    @Test
    @DisplayName("예약 조회 성공")
    public void findReservationSuccess() {
        // given
        ReservationFindRequest request = ReservationFindRequest.builder()
                .reservationName("JH")
                .reservationPhoneNumber("010-1234-4567")
                .build();
        // expect
        List<ReservationFindResponse> findReservation =
                reservationService.findReservation(request);
        Assertions.assertThat(findReservation).isNotEmpty();
    }

    @Test
    @DisplayName("예약 조회 실패")
    public void findReservationFail() {
        // given
        ReservationFindRequest request = ReservationFindRequest.builder()
                .reservationName("HH")
                .reservationPhoneNumber("010-1234-4567")
                .build();
        // expect
        Assertions.assertThatThrownBy(() -> reservationService.findReservation(request))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    @DisplayName("예약 취소 성공")
    public void cancelReservationSuccess() {
        ReservationCancelRequest request = ReservationCancelRequest.builder()
                .reservationId(20)
                .build();

        Reservation targetReservation = reservationRepository.findById(20).get();
        ReservationCancelResponse cancelReservation = reservationService.cancelReservation(request);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(cancelReservation.getPerformanceId()).isEqualTo(targetReservation.getPerformance().getId());
        softAssertions.assertThat(cancelReservation.getRound()).isEqualTo(targetReservation.getRound());
        softAssertions.assertThat(cancelReservation.getSeat()).isEqualTo(targetReservation.getSeat());
        softAssertions.assertThatThrownBy(() -> reservationRepository.findById(20).orElseThrow(() -> new ReservationNotFoundException(ErrorCode.RESERVATION_NOT_FOUND)))
                .isInstanceOf(ReservationNotFoundException.class);
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("예약 취소 실패 (잘못된 예약 ID)")
    public void cancelReservationFail() {
        ReservationCancelRequest request = ReservationCancelRequest.builder()
                .reservationId(100)
                .build();
        Assertions.assertThatThrownBy(() -> reservationService.cancelReservation(request))
                .isInstanceOf(ReservationNotFoundException.class);
    }
}
