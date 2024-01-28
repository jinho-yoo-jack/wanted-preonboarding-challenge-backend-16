package com.wanted.preonboarding.ticket.service;

import static com.wanted.preonboarding.ticket.domain.entity.ReservationStatus.AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;
import com.wanted.preonboarding.ticket.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.service.dto.request.PerformanceCheckRequestDto;

@ExtendWith(MockitoExtension.class)
class PerformanceServiceTest {

    @Mock
    private PerformanceRepository performanceRepository;

    @InjectMocks
    private PerformanceService performanceService;

    @Test
    void 예약_가능한_공연_조회() {
        // given
        final PerformanceCheckRequestDto request = new PerformanceCheckRequestDto(AVAILABLE);

        given(performanceRepository.findAllByStatus(any()))
                .willReturn(List.of(Performance.builder()
                        .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                        .name("공연 이름")
                        .price(10000)
                        .round(1)
                        .type(PerformanceType.CONCERT)
                        .status(ReservationStatus.AVAILABLE)
                        .start_date(LocalDateTime.of(2024, 2, 1, 10, 0))
                        .build()));

        // when
        final List<PerformanceCheckResponseDto> responses = performanceService.getPerformances(request);

        // then
        assertThat(responses).hasSize(1);
    }
}
