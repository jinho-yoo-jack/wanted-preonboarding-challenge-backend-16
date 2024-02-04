package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.exception.customException.InsufficientBalanceException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional(readOnly = true)
    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
                .stream()
                .map(PerformanceInfo::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public UUID getPerformanceId(PerformanceIdRequest performanceIdRequest) {

        Performance performance = performanceRepository.findByNameAndTypeAndRoundAndStartDate(

                performanceIdRequest.getPerformanceName(),
                performanceIdRequest.getPerformanceType(),
                performanceIdRequest.getPerformanceRound(),
                performanceIdRequest.getStartDate()

        ).orElseThrow(() -> new NoResultException("공연을 찾을 수 없습니다."));

        return performance.getId();

    }

    @Transactional(readOnly = true)
    public PerformanceInfo getPerformanceById(String performanceId) {
        UUID uuid = UUID.fromString(performanceId);
        Performance performance = performanceRepository.findById(uuid).orElseThrow(() -> new NoResultException("해당 Id로 공연을 찾을 수 없습니다."));
        return PerformanceInfo.of(performance);


    }

    //TODO: User테이블에 잔고,예약상태저장,

    //예약하기
    // 예약할 공연 정보를 예약 테이블에 저장
    // 공연 id는 유니크
    // 예약하고자 하는 공연id로 예약 가능한지 확인
    // 예약 가능하면 예약신청하기

    @Transactional
    public ReserveInfo reserve(ReserveInfo reserveInfo) {

        Performance performance = performanceRepository.findById(reserveInfo.getPerformanceId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 공연을 찾을 수 없습니다."));

        int price = performance.getPrice();
        // 계산 결과
        boolean calculated = reserveInfo.CalculateAmount(price);

        if (!calculated) {
            throw new InsufficientBalanceException("보유하고 있는 돈이 부족합니다");
        }
        //  예매 진행
        try {
            reservationRepository.save(Reservation.of(reserveInfo));
            reserveInfo.setReservationStatusToAPPROVED();

        } catch (DataIntegrityViolationException e) {

            reserveInfo.setReservationStatusToREJECTED();
            throw new DataIntegrityViolationException("이미 예약된 공연입니다");
        }
        performance.setDisableReservation();
        performanceRepository.save(performance);

        return reserveInfo;


    }


    @Transactional(readOnly = true)
    public List<Reservation> checkReservation(CheckReserveRequest checkReserveRequest) {
        return reservationRepository.findByNameAndPhoneNumber(checkReserveRequest.getName(), checkReserveRequest.getPhoneNumber()).orElseThrow(
                () -> new NoResultException("해당하는 데이터를 찾을 수 없습니다."));

    }

    //TODO 예외처리하기
    // 예약 취소했을 때
    // 돈 돌려주기.
    @Transactional
    public boolean cancelReserve(CancelReserveRequest cancelReserveRequest) {
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
