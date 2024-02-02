package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.RequestReserveQueryDto;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith({SpringExtension.class})
public class TicketSellerTest {
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TicketSeller ticketSeller;

    @AfterEach
    void deleteReservations() {
        reservationRepository.deleteAll();
        performanceRepository.deleteAll();
    }

    @Test
    public void getAllPerformanceInfoList() {
        System.out.println("RESULT => " + performanceRepository.findAll());


    }

    @DisplayName("예약 시스템 테스트")
    @Test
    public void ticketReserveTest() {

        //given
        Performance performance = Performance.builder()
                .name("whiplash")
                .price(20000)
                .round(2)
                .type(0)
                .start_date(Date.valueOf("2024-01-30"))
                .isReserve("enable")
                .build();
        performanceRepository.save(performance);

        ReserveInfo info = ReserveInfo.builder()
                .performanceId(performance.getId())
                .reservationName("이곰돌") // 고객의 이름
                .reservationPhoneNumber("010-2222-2222") // 휴대 전화
                .reservationStatus("enable") // 예약; 취소;
                .amount(10000) // 결제 가능한 금액(잔고)
                .round(1) // 회차
                .line('A') // 좌석 정보
                .seat(1) // 좌석 정보
                .build();

        //when
        boolean flag = ticketSeller.reserve(info);

        if(flag) {
            //then
            Performance resultPerformance = performanceRepository.findByIdAndRound(performance.getId(), performance.getRound()).get();
            Reservation resultReservation = reservationRepository.findByPerformanceIdAndRoundAndLineAndSeat(performance.getId(),performance.getRound(), info.getLine(), info.getSeat()).get();;
            assertThat(resultPerformance.getId()).isEqualTo(resultReservation.getPerformanceId());
        }
    }


    @DisplayName("예약 조회 시스템 테스트")
    @Test
    public void ticketReserveInfoTest() {

        //given
        Performance performance = Performance.builder()
                .name("whiplash")
                .price(20000)
                .round(2)
                .type(0)
                .start_date(Date.valueOf("2024-01-30"))
                .isReserve("enable")
                .build();
        performanceRepository.save(performance);

        Reservation reservation = Reservation.builder()
                .performanceId(performance.getId())
                .name("이곰돌")
                .phoneNumber("010-2222-2222")
                .build();
        reservationRepository.save(reservation);
        RequestReserveQueryDto dto = RequestReserveQueryDto.of(reservation.getName(), reservation.getPhoneNumber());


        //when
        Reservation resultReservation = reservationRepository.findByNameAndPhoneNumber(reservation.getName(), reservation.getPhoneNumber()).get();


        //then
        assertThat(dto.getReservationName()).isEqualTo(resultReservation.getName());
        assertThat(dto.getReservationPhoneNumber()).isEqualTo(resultReservation.getPhoneNumber());
    }
}
