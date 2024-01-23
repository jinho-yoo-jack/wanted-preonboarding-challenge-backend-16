package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TicketSellerTest {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Test
    public void getAllPerformanceInfoList() {
        System.out.println("RESULT => " + performanceRepository.findAll());

    }
}
