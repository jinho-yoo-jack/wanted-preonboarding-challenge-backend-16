package com.wanted.preonboarding.ticket.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TicketSellerTest {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private TicketSeller ticketSeller;

    @Test
    public void getAllPerformanceInfoList() {
        System.out.println("RESULT => " + performanceRepository.findAll());
    }

    @Test
    void reserve() {


        // when

    }
}
