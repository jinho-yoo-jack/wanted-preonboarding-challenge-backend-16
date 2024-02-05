package com.wanted.preonboarding.ticket.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;
import com.wanted.preonboarding.ticket.domain.event.PerformanceCancelEvent;
import com.wanted.preonboarding.ticket.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.repository.ReservationRepository;

@SpringBootTest
@RecordApplicationEvents
class NotificationServiceTest {

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ApplicationEvents events;

    @Test
    void testEvent() {
        // given
        final Performance performance = Performance.builder()
                .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .name("공연 이름")
                .price(10000)
                .round(1)
                .type(PerformanceType.CONCERT)
                .status(ReservationStatus.AVAILABLE)
                .start_date(LocalDateTime.of(2024, 2, 1, 10, 0))
                .build();

        performanceRepository.save(performance);

        final ReserveInfo reserveInfo1 = ReserveInfo.builder()
                .line('A')
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .seat(3)
                .round(performance.getRound())
                .build();

        final ReserveInfo reserveInfo2 = ReserveInfo.builder()
                .line('A')
                .reservationName("강남")
                .reservationPhoneNumber("010-1234-5644")
                .seat(1)
                .round(performance.getRound())
                .build();

        reservationRepository.save(Reservation.of(reserveInfo1, performance));
        reservationRepository.save(Reservation.of(reserveInfo2, performance));

        // when
        performanceService.cancel(performance.getId());
        
        // then
        final int count = (int) events.stream(PerformanceCancelEvent.class).count();
        assertThat(count).isEqualTo(1);
    }
}
