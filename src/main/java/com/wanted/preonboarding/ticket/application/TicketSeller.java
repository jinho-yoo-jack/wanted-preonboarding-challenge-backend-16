package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReservePerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.discount.DiscountPolicy;
import com.wanted.preonboarding.ticket.domain.dto.discount.MembershipDiscountPolicy;
import com.wanted.preonboarding.ticket.domain.entity.*;
import com.wanted.preonboarding.ticket.exception.ReservationException;
import com.wanted.preonboarding.ticket.infrastructure.repository.*;
import com.wanted.preonboarding.ticket.util.MessageGenerator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final UserRepository userRepository;
    private final UserPerformanceRepository userPerformanceRepository;

    public List<PerformanceInfo> getAllPerformanceInfoList(String isEnable) {
        return performanceRepository.findByIsReserve(isEnable)
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    @Transactional
    public ReservePerformanceInfo reserve(ReserveInfo reserveInfo) {
        // performanceId로 해당 ID의 공연 정보를 조회한다.
        Performance performance = performanceRepository
                .findById(reserveInfo.getPerformanceId())
                .orElseThrow(EntityNotFoundException::new);

        // 공연은 총 두가지로 예약가능/불가능 상태가 존재한다.
        String isPerformanceReserve = performance.getIsReserve();

        // CASE1. 공연 예약이 가능한 경우
        if (isPerformanceReserve.equalsIgnoreCase("enable")) {
            // 예약이 가능할 경우 공연의 PK로 좌석의 정보를 호출한다.
            PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findByPerformanceIdJpql(performance.getId(), reserveInfo.getRound(), reserveInfo.getLine(), reserveInfo.getSeat());

            // CASE1. 해당 좌석이 예약 가능한 경우
            if (performanceSeatInfo.getIsReserve().equalsIgnoreCase("enable")) {
                // 해당 좌석 예약 처리
                performanceSeatInfo.changeDisableReserveStatus();

                // 예약 진행 (여기서는 회원의 등급에 따라 할인 적용)
                User user = userRepository.findByNameAndPhoneNumber(reserveInfo.getReservationName(), reserveInfo.getReservationPhoneNumber());
                long myAccountMoney = user.getAmount();
                int performancePrice = performance.getPrice();

                // 회원 등급에 따른 할인 정책 적용
                DiscountPolicy discountPolicy = new MembershipDiscountPolicy();
                int discountedPerformancePrice = discountPolicy.getDiscountPolicy(performancePrice, user.getMembership());
                user.payAfterRemainingAmount(myAccountMoney - discountedPerformancePrice);

                // 요금이 부족한 경우
                if((user.getAmount()) < 0) {
                    throw new ReservationException("요금이 부족합니다.");
                }
                
                // 요금이 충분한 경우 예약 진행
                Reservation savedReservationInfo = reservationRepository.save(Reservation.of(reserveInfo));

                // 결과 리턴
                return ReservePerformanceInfo.of(savedReservationInfo, performance);
            }
            // CASE2. 해당 좌석이 예약 불가능한 경우
            else {
                // 해당 좌석은 예약이 불가능하기 때문에 ReservationException 호출
                throw new ReservationException("이미 예약된 좌석입니다.");
            }
        }
        // CASE2. 공연 예약이 불가능한 경우
        else {
            // 해당 공연은 예약이 불가능하기 때문에 ReservationException 호출
            throw new ReservationException("해당 공연은 예약이 불가능합니다.");
        }
    }

    @Transactional
    public void cancel(ReserveInfo reserveInfo) {
        // performanceId로 해당 ID의 공연 정보를 조회한다.
        Performance performance = performanceRepository
                .findById(reserveInfo.getPerformanceId())
                .orElseThrow(EntityNotFoundException::new);

        // 현재 예약자의 좌석 정보를 호출하여 좌석을 예약 가능 상태로 변경한다.
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findByPerformanceIdJpql(performance.getId(), reserveInfo.getRound(), reserveInfo.getLine(), reserveInfo.getSeat());
        performanceSeatInfo.changeEnableReserveStatus();

        // 공연ID, 이름, 휴대폰번호로 예약 정보를 조회한다.
        Reservation reservation = reservationRepository.findByPerformanceIdAndNameAndPhoneNumber(reserveInfo.getPerformanceId(), reserveInfo.getReservationName(), reserveInfo.getReservationPhoneNumber());

        // 예약된 정보가 없을 경우 예외처리
        if (reservation == null) {
            throw new ReservationException("예약된 정보가 존재하지 않습니다.");
        }

        // 예약된 정보 삭제
        reservationRepository.delete(reservation);

        // 취소표에 대한 알람 전송
        String message = MessageGenerator.performanceCancelMessage(reservation);

        List<UserPerformance> userPerformances = userPerformanceRepository.findByPerformanceId(reserveInfo.getPerformanceId());
        for (UserPerformance userPerformance : userPerformances) {
            User user = userRepository
                    .findById(userPerformance.getUserId())
                    .orElseThrow(EntityNotFoundException::new);
            user.update(message);
        }
    }

    public ReservePerformanceInfo getReservationInfo(ReserveInfo reserveInfo) {
        // 고객의 이름, 고객의 휴대폰번호로 예약정보 조회
        String reservationName = reserveInfo.getReservationName();
        String reservationPhone = reserveInfo.getReservationPhoneNumber();
        Reservation reservation = reservationRepository.findByNameAndPhoneNumber(reservationName, reservationPhone);

        // CASE1. 예약된 정보가 있을 경우
        if (reservation != null) {
            // 예약된 공연 테이블의 고유키로 공연 정보 조회
            Performance performance = performanceRepository
                    .findById(reserveInfo.getPerformanceId())
                    .orElseThrow(EntityNotFoundException::new);

            return ReservePerformanceInfo.of(reservation, performance);
        }
        // CASE2. 예약된 정보가 존재하지 않을 경우
        else {
            throw new ReservationException("예약된 정보가 존재하지 않습니다.");
        }
    }

    @Transactional
    public void registSubscription(ReserveInfo reserveInfo) {
        // performanceId로 해당 ID의 공연 정보를 조회한다.
        Performance performance = performanceRepository
                .findById(reserveInfo.getPerformanceId())
                .orElseThrow(EntityNotFoundException::new);

        // 사용자 정보를 조회한다.
        User user = userRepository
                .findByNameAndPhoneNumber(reserveInfo.getReservationName(), reserveInfo.getReservationPhoneNumber());

        // 알림 설정 등록
        UserPerformance userPerformance = UserPerformance
                .builder()
                .userId(user.getId())
                .performanceId(performance.getId())
                .build();

        userPerformanceRepository.save(userPerformance);
    }

    @Transactional
    public void unregistSubscription(ReserveInfo reserveInfo) {
        // performanceId로 해당 ID의 공연 정보를 조회한다.
        Performance performance = performanceRepository
                .findById(reserveInfo.getPerformanceId())
                .orElseThrow(EntityNotFoundException::new);

        // 사용자 정보를 조회한다.
        User user = userRepository
                .findByNameAndPhoneNumber(reserveInfo.getReservationName(), reserveInfo.getReservationPhoneNumber());

        // 알림 설정 해제
        UserPerformance userPerformance = userPerformanceRepository.findByUserIdAndPerformanceId(user.getId(), performance.getId());
        userPerformanceRepository.deleteById(userPerformance.getId());
    }
}