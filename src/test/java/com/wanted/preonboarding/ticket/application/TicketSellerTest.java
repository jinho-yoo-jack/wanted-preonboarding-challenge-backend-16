package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TicketSellerTest {
    @Mock
    private PerformanceRepository performanceRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private PerformanceSeatRepository performanceSeatRepository;
    @Mock
    private AlarmApp alarmApp;
    @InjectMocks
    private TicketSeller ticketSeller;
    private final UUID testUUID = UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002");
    private Performance testPerformance;
    private Reservation reservation;
    private PerformanceSeatInfo testPerformanceSeatInfo;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        testPerformance = Performance.builder()
                .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .price(10000)
                .type(PerformanceType.CONCERT)
                .name("testPerformanceName")
                .isReserve("enable")
                .round(1)
                .start_date(new Date(125, 3, 30))
                .build();


    }


    @Test
    public void getAllPerformanceInfoList() {
        //given

        when(performanceRepository.findByIsReserve("enable")).thenReturn(Collections.singletonList(testPerformance));

        //when
        List<PerformanceInfo> performanceInfos = ticketSeller.getAllPerformanceInfoList();

        //then
        verify(performanceRepository, times(1)).findByIsReserve("enable");
        assertEquals(1, performanceInfos.size());
        assertEquals(testPerformance.getId(), performanceInfos.get(0).getPerformanceId());
    }

    @Test
    @DisplayName("Test reserve")
    void testReserve() throws Exception {
        //given
        List<String> seats = Arrays.asList("A1");
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .reservationName("유진호")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(20000)
                .round(1)
                .seats(seats)
                .build();

        testPerformanceSeatInfo = PerformanceSeatInfo.convertSeatInfo(seats.get(0));
        reservation = Reservation.of(reserveInfo, testPerformanceSeatInfo);

        when(performanceRepository.findById(any())).thenReturn(Optional.ofNullable(testPerformance));
        when(performanceSeatRepository
                .findByPerformanceIdAndSeatLineAndSeatNumber(testPerformance.getId(), testPerformanceSeatInfo.getSeatLine(), testPerformanceSeatInfo.getSeatNumber()))
                .thenReturn(Optional.of(testPerformanceSeatInfo));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        //when
        List<ResponseReserveInfo> reserveInfoList = ticketSeller.reserve(reserveInfo);
        //then
        assertEquals(reserveInfoList.size(),1);
    }

    @Test
    @DisplayName("Test DiscountReserve")
    void testDiscountReserve() throws Exception {
        //given
        List<String> seats = Arrays.asList("A1");
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .reservationName("유진호")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(20000)
                .round(1)
                .seats(seats)
                .discountPolicy("0.1")
                .build();
        PerformanceSeatInfo testSeatInfo = PerformanceSeatInfo.convertSeatInfo(seats.get(0));
        Reservation reservation = Reservation.of(reserveInfo,testSeatInfo);

        when(performanceRepository.findById(reserveInfo.getPerformanceId())).thenReturn(Optional.ofNullable(testPerformance));
        when(performanceSeatRepository
                .findByPerformanceIdAndSeatLineAndSeatNumber(testPerformance.getId(), testSeatInfo.getSeatLine(),testSeatInfo.getSeatNumber()))
                .thenReturn(Optional.of(testSeatInfo));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        //when
        ticketSeller.reserve(reserveInfo);

        //then
        assertEquals(11000, reserveInfo.getAmount()); // 20000 - 9000
    }

    @Test
    @DisplayName("Get Reserve Info")
    void getReserveInfo() throws Exception {
        //given
        GetReservationRequestDto requestDto = new GetReservationRequestDto("유진호","010-1234-1234");
        List<String> seats = Arrays.asList("A1");
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .reservationName("유진호")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(20000)
                .round(1)
                .seats(seats)
                .build();

        testPerformanceSeatInfo = PerformanceSeatInfo.convertSeatInfo(seats.get(0));
        testPerformanceSeatInfo.setPerformance(testPerformance);

        reservation = Reservation.of(reserveInfo, testPerformanceSeatInfo);

        when(reservationRepository.findByNameAndPhoneNumber(requestDto.getReservationName(),requestDto.getReservationPhoneNumber()))
                .thenReturn(Collections.singletonList(reservation));
        when(performanceRepository.findById(any())).thenReturn(Optional.ofNullable(testPerformance));
        when(performanceSeatRepository
                .findByPerformanceIdAndSeatLineAndSeatNumber(reservation.getPerformanceId(),reservation.getLine(),reservation.getSeat()))
                .thenReturn(Optional.ofNullable(testPerformanceSeatInfo));

        //when
        List<ResponseReserveInfo> result = ticketSeller.getReserveInfo(requestDto);

        //then
        assertEquals(1, result.size());
        assertEquals(reserveInfo.getReservationName(), result.get(0).getReservationName());
    }
}
