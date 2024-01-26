package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TicketSellerTest {
    @Autowired
    private PerformanceRepository performanceRepository;
    
    @Autowired
    private PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @Autowired
    TicketSeller ticketSeller;
    @Test
    public void getAllPerformanceInfoList() {
        System.out.println("RESULT => " + performanceRepository.findAll());

    }
    
    @Test
    public void printSeatInfo(){
        PerformanceSeatInfo seatInfo = performanceSeatInfoRepository.findById(1).orElseThrow(() -> new EntityNotFoundException("원하는 엔티티가 존재하지 않음"));
        System.out.println("Round: " + Integer.toString(seatInfo.getPerformance().getRound()) + " UUID: " + seatInfo.getPerformance().getId().toString());
    }

    @Test
    public void insertSeatInfo(){
        Date start = new Date(20240205L);
        Performance performance = Performance.builder()
            .id(UUID.randomUUID())
            .name("test")
            .price(10023)
            .round(2)
            .type(3)
            .start_date(start)
            .isReserve("enable")
            .build();
        PerformanceSeatInfo seatInfo = PerformanceSeatInfo.builder()
            .performance(performance)
            .gate(3)
            .line('B')
            .seat(2)
            .isReserve("reserve")
            .build();
        performanceSeatInfoRepository.save(seatInfo);

    }

    @Test
    public void ticketSellerReserveTest(){
        Date start = new Date(20240205L);
        Performance performance = Performance.builder()
            .id(UUID.randomUUID())
            .name("test")
            .price(10023)
            .round(2)
            .type(3)
            .start_date(start)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .isReserve("enable")
            .build();
        performanceRepository.save(performance);
        ReserveInfo reserveInfo = new ReserveInfo();
        reserveInfo.setPerformanceId(performance.getId());
        reserveInfo.setReservationName("김철수");
        reserveInfo.setReservationPhoneNumber("010-2121-2121");
        reserveInfo.setReservationStatus("reserved");
        reserveInfo.setAmount(500000L);
        reserveInfo.setRound(1);
        reserveInfo.setLine('A');
        reserveInfo.setSeat(2);


        boolean result = ticketSeller.reserve(reserveInfo);

        Assertions.assertTrue(result);


    }

}
