package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.Column;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TicketSellerTest {
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TicketSeller ticketSeller;

    @Test
    public void getAllPerformanceInfoList() {
        System.out.println("RESULT => " + performanceRepository.findAll());


    }


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
            Performance resultPerformance = performanceRepository.findById(performance.getId()).get();
            Reservation resultReservation = reservationRepository.findByUUID(resultPerformance.getId());
            assertThat(resultPerformance.getId()).isEqualTo(resultReservation.getPerformanceId());
        }
    }

}
