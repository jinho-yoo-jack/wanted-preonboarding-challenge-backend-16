package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.domain.dto.request.CancelReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.CreateAlarmRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.ReadReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.CancelReservationResponse;
import com.wanted.preonboarding.ticketing.domain.dto.response.CreateReservationResponse;
import com.wanted.preonboarding.ticketing.domain.dto.response.ReadReservationResponse;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.Reservation;
import com.wanted.preonboarding.ticketing.repository.AlarmRepository;
import com.wanted.preonboarding.ticketing.repository.PerformanceRepository;
import com.wanted.preonboarding.ticketing.repository.ReservationRepository;
import com.wanted.preonboarding.ticketing.service.alarm.AlarmService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private AlarmService alarmService;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private PerformanceRepository performanceRepository;


    @AfterEach
    void deleteReservations() {
        reservationRepository.deleteAll();
        alarmRepository.deleteAll();
    }

    private Reservation createReservationForTest() {
        Performance performance = performanceRepository.getReferenceById(UUID.fromString("a95adf41-ba11-11ee-8083-0242ac130002"));
        Reservation reservation = Reservation.builder()
                .name("구길동")
                .phoneNumber("010-5678-1235")
                .performance(performance)
                .round(1)
                .gate(1)
                .line("A")
                .seat(3)
                .build();

        return reservationRepository.save(reservation);
    }

    private void createAlarmFormTest() {
        CreateAlarmRequest createAlarmRequest = CreateAlarmRequest.builder()
                .performanceId(UUID.fromString("a95adf41-ba11-11ee-8083-0242ac130002"))
                .customerName("유길동")
                .phoneNumber("010-5678-5432")
                .build();

        alarmService.createAlarm(createAlarmRequest);
    }

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

    @DisplayName("예약 정보를 정상적으로 불러오는지 확인")
    @Test
    void readReservation() {
        // given
        createReservationForTest();
        ReadReservationRequest reservationRequest = ReadReservationRequest.builder()
                .reservationName("구길동")
                .phoneNumber("010-5678-1235")
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<ReadReservationResponse> readReservationResponses = reservationService.readReservations(reservationRequest, pageable);

        // then
        assertThat(readReservationResponses).isNotNull();
    }

    @DisplayName("예약을 정상적으로 취소하고 알람을 보내는지 확인")
    @Test
    void cancelReservation() {
        // given
        Reservation reservation = createReservationForTest();
        createAlarmFormTest();

        CancelReservationRequest cancelReservationRequest = CancelReservationRequest.builder()
                .reservationId(reservation.getId())
                .reservationSeatId((long) reservation.getSeat())
                .build();

        // when
        CancelReservationResponse cancelReservationResponses = reservationService.cancelReservation(cancelReservationRequest);

        // then
        assertThat(reservationRepository.existsById(reservation.getId())).isFalse();
        assertThat(cancelReservationResponses).isNotNull();
    }
}