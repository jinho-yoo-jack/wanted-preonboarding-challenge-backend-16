package com.wanted.preonboarding.ticket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;
import com.wanted.preonboarding.ticket.exception.EntityNotFound;
import com.wanted.preonboarding.ticket.exception.InsufficientPaymentException;
import com.wanted.preonboarding.ticket.exception.PerformanceSeatReserveValidationException;
import com.wanted.preonboarding.ticket.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.service.dto.request.ReservationRequestDto;
import com.wanted.preonboarding.ticket.service.dto.response.ReservationResponseDto;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Performance performance;

    @BeforeEach
    void setUp() {
        performance = createPerformance();
    }

    @Test
    void 예약() {
        // given
        final ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .amount(10000)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        final PerformanceSeatInfo seatInfo = PerformanceSeatInfo.builder()
                .id(1L)
                .performance(performance)
                .status(ReservationStatus.AVAILABLE)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        given(performanceRepository.findById(any()))
                .willReturn(Optional.of(performance));
        given(performanceSeatInfoRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                requestDto.performanceId(), requestDto.round(), requestDto.line(), requestDto.seat()))
                .willReturn(Optional.of(seatInfo));

        // when
        final ReservationResponseDto responseDto = reservationService.reserve(requestDto);

        // then
        assertAll(
                () -> assertThat(responseDto.reservationName()).isEqualTo("홍길동"),
                () -> assertThat(responseDto.reservationPhoneNumber()).isEqualTo("010-1234-5678"),
                () -> assertThat(responseDto.performanceId()).isEqualTo(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002")),
                () -> assertThat(responseDto.performanceName()).isEqualTo("공연 이름"),
                () -> assertThat(responseDto.amount()).isEqualTo(0),
                () -> assertThat(responseDto.round()).isEqualTo(1),
                () -> assertThat(responseDto.line()).isEqualTo('B'),
                () -> assertThat(responseDto.seat()).isEqualTo(1)
        );
    }

    @Test
    void 공연이_존재하지_않으면_예외_발생() {
        // given
        given(performanceRepository.findById(any()))
                .willThrow(new EntityNotFound("Not Found Performance"));

        // when
        // then
        final ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .amount(10000)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        assertThatThrownBy(() -> reservationService.reserve(requestDto))
                .isInstanceOf(EntityNotFound.class);
    }

    @Test
    void 좌석이_존재하지_않는_경우_예외_발생() {
        // given
        given(performanceRepository.findById(any()))
                .willReturn(Optional.of(performance));
        given(performanceSeatInfoRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                any(UUID.class), anyInt(), anyChar(), anyInt())
        ).willThrow(new EntityNotFound("Not Found Seat"));

        // when
        // then
        final ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .amount(10000)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        assertThatThrownBy(() -> reservationService.reserve(requestDto))
                .isInstanceOf(EntityNotFound.class);
    }

    @ParameterizedTest
    @EnumSource(value = ReservationStatus.class, names = {"OCCUPIED", "DISABLED"})
    void 좌석이_예약_불가능한_경우_예외_발생(final ReservationStatus status) {
        // given
        given(performanceRepository.findById(any()))
                .willReturn(Optional.of(performance));

        final PerformanceSeatInfo seatInfo = PerformanceSeatInfo.builder()
                .performance(performance)
                .status(status)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        given(performanceSeatInfoRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                any(UUID.class), anyInt(), anyChar(), anyInt())
        ).willReturn(Optional.of(seatInfo));

        // when
        // then
        final ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .amount(10000)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        assertThatThrownBy(() -> reservationService.reserve(requestDto))
                .isInstanceOf(PerformanceSeatReserveValidationException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {9999, 5000, 3000, 1000})
    void 구매_금액이_부족한_경우_예외_발생(final int amount) {
        // given
        given(performanceRepository.findById(any()))
                .willReturn(Optional.of(performance));

        final PerformanceSeatInfo seatInfo = PerformanceSeatInfo.builder()
                .performance(performance)
                .status(ReservationStatus.AVAILABLE)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        given(performanceSeatInfoRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                any(UUID.class), anyInt(), anyChar(), anyInt())
        ).willReturn(Optional.of(seatInfo));

        // when
        // then
        final ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .amount(amount)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        assertThatThrownBy(() -> reservationService.reserve(requestDto))
                .isInstanceOf(InsufficientPaymentException.class);
    }

    private Performance createPerformance() {
        return Performance.builder()
                .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .name("공연 이름")
                .price(10000)
                .status(ReservationStatus.AVAILABLE)
                .start_date(LocalDateTime.of(2024, 2, 1, 10, 0))
                .build();
    }
}
