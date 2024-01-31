package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TicketSellerTest {
    @Autowired
    private PerformanceRepository performanceRepository;

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

        System.out.println("performance.getId() = " + performance.getId());
    }

}
