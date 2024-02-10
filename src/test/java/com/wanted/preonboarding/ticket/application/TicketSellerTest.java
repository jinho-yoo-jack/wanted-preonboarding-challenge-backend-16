package com.wanted.preonboarding.ticket.application;


import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ticket.domain.dto.request.FindReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.PerformanceListRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReserveInfoRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReserveResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceListResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
public class TicketSellerTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private TicketSeller ticketSeller;
    @Autowired
    private PerformanceRepository performanceRepository;

    private static Performance performance;

    @BeforeEach
    void initData() throws ParseException {
        performance = Performance.create("레베카", 100000, 1, 0, valueOf("2024-01-20 19:30:00"), "enable");

        // when
        em.persist(performance);

        for (int i = 1; i <= 4; i++) {
            em.persist(PerformanceSeatInfo.create(performance, 1, 1, 'A', i, "enable"));
        }

        reserve();
    }

    @Test
    @DisplayName("공연을 예약한다.")
    void reserve() {
        ReserveInfoRequest request = new ReserveInfoRequest();
        request.setReservationName("홍길동");
        request.setReservationPhoneNumber("010-1234-1234");
        request.setAmount(200000);
        request.setPerformanceId(performance.getId());
        request.setRound(1);
        request.setLine('A');
        request.setSeat(1);
        ticketSeller.reserve(request);
    }

    @Test
    void findReserve() {
        // given
        FindReserveRequest request = new FindReserveRequest();
        request.setName("홍길동");
        request.setPhoneNumber("010-1234-1234");
        // when
        FindReserveResponse result = ticketSeller.findReserveInfo(request);
        log.info("result => {}", result);
    }

    @Test
    void findAllCond() {
        // given
        PerformanceListRequest request = new PerformanceListRequest();
        request.setSrchPerformanceCond("enable");
        List<PerformanceListResponse> results = ticketSeller.findAvailablePerformance(request);
        log.info("results => {}", results);
    }

    @Test
    void waitReservation() {
        // given
        Performance result = performanceRepository.findById(performance.getId())
            .orElseThrow(() -> new IllegalArgumentException("공연이 존재하지 않습니다."));
        if (result.getIsReserve().equalsIgnoreCase("disable")) {

        }
        // when


        // then

    }

    private Date valueOf(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }
}
