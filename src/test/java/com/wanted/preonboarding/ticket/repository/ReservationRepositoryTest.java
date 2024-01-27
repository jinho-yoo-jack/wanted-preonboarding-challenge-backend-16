package com.wanted.preonboarding.ticket.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    private Performance performance;

    @BeforeEach
    void setUp() {
        performance = Performance.builder()
                .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .name("레베카")
                .type(PerformanceType.CONCERT)
                .start_date(LocalDateTime.of(2021, 10, 10, 10, 10))
                .status(ReservationStatus.AVAILABLE)
                .price(100000)
                .build();
        performanceRepository.save(performance);
    }

    @Test
    void 예약자_이름으로_예약_조회() {
        // given
        final Reservation reservation = Reservation.builder()
                .name("김철수")
                .phoneNumber("010-1234-5678")
                .performance(performance)
                .round(1)
                .gate(1)
                .line('A')
                .seat(1)
                .build();
        System.out.println("createdAt: " + reservation.getCreatedAt());
        reservationRepository.save(reservation);
        reservationRepository.save(Reservation.builder()
                .name("김철수")
                .phoneNumber("010-1234-5678")
                .performance(performance)
                .round(1)
                .gate(1)
                .line('B')
                .seat(3)
                .build());

        final String name = "김철수";

        // when
        final List<Reservation> reservations = reservationRepository.findAllByName(name);

        // then
        Assertions.assertThat(reservations).hasSize(2);
    }
}
