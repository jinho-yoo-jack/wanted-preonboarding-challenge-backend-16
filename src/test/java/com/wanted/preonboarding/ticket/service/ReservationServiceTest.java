package com.wanted.preonboarding.ticket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;
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
