package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private long totalAmount = 0L;

    @Transactional
    public PerformanceInfo addPerformance(CreatePerformance createPerformance) {
        Optional.ofNullable(performanceRepository.findByNameAndTypeAndRoundAndStartDate(
                createPerformance.getName(),
                createPerformance.getType(),
                createPerformance.getRound(),
                createPerformance.getStartDate()
        )).orElseThrow(() ->
                new DataIntegrityViolationException("해당 공연이 이미 존재합니다.")
        );


        UUID uuid = UUID.randomUUID();

        Performance performance = Performance.builder().
                id(uuid)
                .name(createPerformance.getName())
                .price(createPerformance.getPrice())
                .round(createPerformance.getRound())
                .type(createPerformance.getType())
                .startDate(createPerformance.getStartDate())
                .isReserve(createPerformance.getIsReserve())
                .build();


        return PerformanceInfo.of(performanceRepository.save(performance));


    }


    public List<PerformanceInfo> getAllPerformanceInfoList() {
        //예약 가능한 공연리스트 가져옴
        return performanceRepository.findByIsReserve("enable")
                .stream()
                .map(PerformanceInfo::of)
                .toList();
    }

    @Transactional
    public UUID getPerformanceId(PerformanceIdRequest performanceIdRequest) {

        Performance performance = performanceRepository.findByNameAndTypeAndRoundAndStartDate(

                performanceIdRequest.getPerformanceName(),
                performanceIdRequest.getPerformanceType(),
                performanceIdRequest.getPerformanceRound(),
                performanceIdRequest.getStartDate()


        );

        return performance.getId();


    }

    public boolean reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        log.info("performanceRepository.findById ID => {}", performanceRepository.findById(reserveInfo.getPerformanceId()));

        Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
                .orElseThrow(EntityNotFoundException::new);

        String enableReserve = info.getIsReserve();

        if (enableReserve.equalsIgnoreCase("enable")) {

            reserveInfo.setReserveInfo("Pending");

            try {

                // 1. 결제
                //공연가격

                int price = info.getPrice();
                // 가진 amount 돈 양
                reserveInfo.setAmount(reserveInfo.getAmount() - price);
                // 2. 예매 진행
                reservationRepository.save(Reservation.of(reserveInfo));


            } catch (Exception e) {
                reserveInfo.setReserveInfo("Rejected");
                reserveInfo.setAmount(reserveInfo.getAmount());


            }

            updatePerformance(info.getId());
            reserveInfo.setReserveInfo("Pending");


            return true;

        } else {
            reserveInfo.setReserveInfo("Approved");

            return false;
        }
    }

    @Transactional
    public void updatePerformance(UUID performanceId) {
        performanceRepository.updateIsReserveStatus(performanceId, "disable");
    }


    @Transactional
    public Reservation checkReservation(CheckReserveRequest checkReserveRequest) {
        return reservationRepository.findByNameAndPhoneNumber(checkReserveRequest.getName(), checkReserveRequest.getPhoneNumber()).orElseThrow(
                () -> new NoSuchElementException("해당하는 데이터를 찾을 수 없습니다."));

    }

    @Transactional
    public boolean cancleReserve(CancelReserveRequest cancelReserveRequest) {
        try {
            reservationRepository.deleteByPerformanceIdAndNameAndPhoneNumberAndRound(
                    cancelReserveRequest.getPerformanceId(),
                    cancelReserveRequest.getReservationName(),
                    cancelReserveRequest.getReservationPhoneNumber(),
                    cancelReserveRequest.getRound()
            );


            return true;
        } catch (EmptyResultDataAccessException exception) {
            throw exception;
        }

    }


}
