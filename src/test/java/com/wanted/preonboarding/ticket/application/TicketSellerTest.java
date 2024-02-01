package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceIdRequest;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TicketSellerTest {
    @Autowired
    private PerformanceRepository performanceRepository;

    //생성시 UUID가 존재하면 추가되지 않도록 pk로
    @Test
    public void addPerformance() {
        UUID uuid = UUID.randomUUID();
        System.out.println("uuid = " + uuid);
        Performance performance = Performance.builder().
                id(uuid)
                .name("브루노마스")
                .price(50000)
                .round(1)
                .type(1)
                .startDate(Date.valueOf("2024-01-31"))
                .isReserve("disable")
                .build();

        performanceRepository.save(performance);

    }

    @Test
    public void getAllPerformanceInfoList() {
        List<Performance> performances = performanceRepository.findAll();
        for (Performance performance : performances) {
            System.out.println("performance = " + performance.getId());

        }
    }

    @Test
    public void findByIdPerformanceInfoList() {
        Performance performance = performanceRepository.findById(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .orElseThrow(EntityNotFoundException::new);

        log.info("performanceId=", performance.getId());
    }


    @Test
    public void getPerformanceId() {
        PerformanceIdRequest performanceIdRequest = PerformanceIdRequest.builder()
                .performanceName("브루노마스")
                .performanceType(1)
                .performanceRound(1)
                .startDate(Date.valueOf("2024-01-31"))
                .build();

        Performance performance = performanceRepository.findByNameAndTypeAndRoundAndStartDate(
                performanceIdRequest.getPerformanceName(),
                performanceIdRequest.getPerformanceType(),
                performanceIdRequest.getPerformanceRound(),
                performanceIdRequest.getStartDate()
        );

        log.info("performanceId=", performance.getId());

    }

}
