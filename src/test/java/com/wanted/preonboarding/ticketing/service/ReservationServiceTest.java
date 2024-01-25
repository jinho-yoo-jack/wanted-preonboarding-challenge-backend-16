package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.CreateReservationResponse;
import com.wanted.preonboarding.ticketing.repository.PerformanceRepository;
import com.wanted.preonboarding.ticketing.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticketing.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @DisplayName("예약이 정상적으로 수행되는지 확인")
    @Test
    void createReservation() {
        // given
        CreateReservationRequest createReservationRequest = CreateReservationRequest.builder()
                .reservationName("금길동")
                .reservationStatus("disable")
                .phoneNumber("010-5678-1235")
                .balance(1000000)
                .performanceId(UUID.fromString("a95adf41-ba11-11ee-8083-0242ac130002"))
                .round(1)
                .gate(1)
                .line("A")
                .seatId(1L)
                .seat(2)
                .build();

        // when
        CreateReservationResponse createReservationResponse = reservationService.createReservation(createReservationRequest);

        // then
        assertThat(createReservationResponse).isNotNull();
        assertThat(createReservationResponse.getReservationName()).isEqualTo(createReservationRequest.getReservationName());
    }


    @Test
    void readReservation() {
    }

    @Test
    void cancelReservation() {
    }
}