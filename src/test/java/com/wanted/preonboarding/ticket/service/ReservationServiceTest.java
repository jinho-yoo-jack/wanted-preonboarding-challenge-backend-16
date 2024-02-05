package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.core.code.ReservationStatus;
import com.wanted.preonboarding.ticket.controller.model.ReservationApplyModel;
import com.wanted.preonboarding.ticket.controller.model.request.ReservationApplyRequest;
import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.domain.PerformanceSeat;
import com.wanted.preonboarding.ticket.domain.Reservation;
import com.wanted.preonboarding.ticket.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.repository.PerformanceSeatRepository;
import com.wanted.preonboarding.ticket.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


@SpringBootTest
@Transactional
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private PerformanceSeatRepository performanceSeatRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    private Performance savePerformance;
    private PerformanceSeat saveSeat;

    @BeforeEach
    void init() {
        savePerformance = performanceRepository.save(createPerformance("레베카"));
        saveSeat = performanceSeatRepository.save(createPerformanceSeat(savePerformance));
    }


    private Performance createPerformance(String title) {
        return Performance.builder()
                .title(title)
                .price(100000)
                .round(1)
                .startDate(LocalDate.now())
                .isReserve(true)
                .build();
    }

    private PerformanceSeat createPerformanceSeat(Performance savePerformance) {
        return PerformanceSeat.builder()
                .seat("R석")
                .performance(savePerformance)
                .build();
    }

    private Reservation saveReservation(String userName, String phoneNumber) {
        return reservationRepository.save(
          Reservation.builder()
                  .title(savePerformance.getTitle())
                  .round(savePerformance.getRound())
                  .amount(savePerformance.getPrice())
                  .userName(userName)
                  .phoneNumber(phoneNumber)
                  .status(ReservationStatus.APPLY)
                  .performance(savePerformance)
                  .performanceSeat(saveSeat)
                  .build()
        );
    }

    @DisplayName("예약 신청이 정상적으로 된다")
    @Test
    void apply() {
        //given
        String userName = "러버덕";
        ReservationApplyRequest applyRequest = ReservationApplyRequest.builder()
                .userName(userName)
                .phoneNumber("010-1234-1234")
                .amountAvailable(2000000)
                .round(savePerformance.getRound())
                .performanceId(savePerformance.getId())
                .performanceSeatId(saveSeat.getId())
                .build();

        //when
        ReservationApplyModel apply = reservationService.apply(applyRequest);

        //then
        assertThat(apply).isNotNull();
        assertThat(apply.getTicketHolder().getUserName()).isEqualTo(userName);
        assertThat(apply.getPerformanceInfo().getTitle()).isEqualTo(savePerformance.getTitle());
    }

    @DisplayName("해당 회원으로 조회된 전체 예약 목록을 가져온다.")
    @Test
    void getReservations() {
        //given
        String userName = "러버덕";
        String phoneNumber = "010-1234-1234";
        Reservation reservation = saveReservation(userName, phoneNumber);

        //when
        List<ReservationApplyModel> reservations = reservationService.getReservations(userName, phoneNumber);

        //then
        assertThat(reservations).hasSize(1)
                .extracting("performanceInfo.title", "ticketHolder.userName")
                .containsExactlyInAnyOrder(
                        tuple(savePerformance.getTitle(), userName)
                );
    }

    @DisplayName("예약 취소 시 예약 취소 상태로 변경된다. (잔여석 안내가 진행).")
    @Test
    void cancel() {
        //given
        String userName = "러버덕";
        String phoneNumber = "010-1234-1234";
        Reservation reservation = saveReservation(userName, phoneNumber);

        //when
        Long cancelId = reservationService.cancel(reservation.getId(), userName, phoneNumber);

        //then
        Reservation findReservation = reservationRepository.findById(cancelId)
                .orElseThrow();

        assertThat(findReservation.getStatus()).isEqualTo(ReservationStatus.CANCEL);
    }
}
