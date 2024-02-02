package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReservePerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.observer.Observer;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.User;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.UserPerformanceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class TicketSellerTest {

    @Autowired
    private TicketSeller ticketSeller;
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PerformanceSeatInfoRepository performanceSeatInfoRepository;
    @Autowired
    private UserPerformanceRepository userPerformanceRepository;

    @BeforeEach
    public void clean() {
        performanceSeatInfoRepository.changeReserveStatus("enable");
        reservationRepository.deleteAll();
        userPerformanceRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 공연 정보를 조회한다.")
    public void getAllPerformanceInfoListTest() {
        System.out.println("RESULT => " + performanceRepository.findAll());
    }

    @Test
    @DisplayName("공연을 예약한다.")
    public void reservePerformanceTest() {
        // Given
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(UUID.fromString("5457e3ed-beb4-11ee-8135-0242ac150002"))
                .reservationName("Ethan")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(200000)
                .round(1)
                .line('A')
                .seat(1)
                .build();

        // When
        ReservePerformanceInfo reservePerformanceInfo = ticketSeller.reserve(reserveInfo);

        // Then
        Assertions.assertEquals("Ethan", reservePerformanceInfo.getReservationName());
        Assertions.assertEquals("010-1234-1234", reservePerformanceInfo.getReservationPhone());
    }

    @Test
    @DisplayName("공연을 예약을 취소한다.")
    public void cancelPerformanceTest() {
        // Given
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(UUID.fromString("5457e3ed-beb4-11ee-8135-0242ac150002"))
                .reservationName("Ethan")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(200000)
                .round(1)
                .line('A')
                .seat(1)
                .build();

        ticketSeller.reserve(reserveInfo);


        // When
        ticketSeller.cancel(reserveInfo);

        // Then
        Assertions.assertNull(reservationRepository.findByNameAndPhoneNumber(reserveInfo.getReservationName(), reserveInfo.getReservationPhoneNumber()));
    }

    @Test
    @DisplayName("공연을 구독한 사용자에게 취소정보에 대한 알람을 전송한다.")
    public void alertCancelPerformanceTest() {
        // Given
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(UUID.fromString("5457e3ed-beb4-11ee-8135-0242ac150002"))
                .reservationName("Ethan")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(200000)
                .round(1)
                .line('A')
                .seat(1)
                .build();

        ticketSeller.reserve(reserveInfo);

        ReserveInfo AnnReserveInfo = ReserveInfo.builder()
                .performanceId(UUID.fromString("5457e3ed-beb4-11ee-8135-0242ac150002"))
                .reservationName("Ann")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(200000)
                .round(1)
                .line('A')
                .seat(1)
                .build();

        ticketSeller.registSubscription(AnnReserveInfo);

        ReserveInfo keyReserveInfo = ReserveInfo.builder()
                .performanceId(UUID.fromString("5457e3ed-beb4-11ee-8135-0242ac150002"))
                .reservationName("Key")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(200000)
                .round(1)
                .line('A')
                .seat(1)
                .build();

        ticketSeller.registSubscription(keyReserveInfo);

        // When
        ticketSeller.cancel(reserveInfo);
    }

    @Test
    @DisplayName("이름과 휴대폰번호로 예약 정보를 조회한다.")
    public void getReservationInfoTest() {
        // Given
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(UUID.fromString("5457e3ed-beb4-11ee-8135-0242ac150002"))
                .reservationName("Ethan")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(200000)
                .round(1)
                .line('A')
                .seat(1)
                .build();

        ticketSeller.reserve(reserveInfo);


        // When
        ReservePerformanceInfo reservePerformanceInfo = ticketSeller.getReservationInfo(reserveInfo);

        // Then
        Assertions.assertEquals("Ethan", reservePerformanceInfo.getReservationName());
        Assertions.assertEquals("010-1234-1234", reservePerformanceInfo.getReservationPhone());
    }

    @Test
    @DisplayName("예매 가능한 모든 공연 조회")
    public void getAllPerformanceInfoTest() {
        // Given
        String isEnable = "enable";

        // When
        List<PerformanceInfo> performances = ticketSeller.getAllPerformanceInfoList(isEnable);

        // Then
        Assertions.assertEquals(1L, performances.size());
    }
}