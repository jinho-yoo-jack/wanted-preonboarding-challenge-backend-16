package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReservationInfo;
import com.wanted.preonboarding.ticket.domain.dto.UserInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeat;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.exception.PerformanceDisable;
import com.wanted.preonboarding.ticket.exception.PerformanceSeatDisable;
import com.wanted.preonboarding.ticket.exception.PriceOver;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final CommonService commonService;
    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatRepository performanceSeatRepository;

    @Transactional
    public ReservationInfo reservation(ReservationInfo reservationInfo) {
        // 유저 정보 탐색
        UserInfo userInfo = commonService.getUserInfo(
                reservationInfo.getUserInfo().getName(),
                reservationInfo.getUserInfo().getPhoneNumber()
        );
        reservationInfo.setUserInfo(userInfo);

        // 공연 정보 탐색
        PerformanceInfo performanceInfo = commonService.getPerformanceInfoByNameAndRound(
                reservationInfo.getPerformanceInfo().getPerformanceName(),
                reservationInfo.getPerformanceInfo().getRound()
        );
        // 공연이 예매 불가하면 => 예외 발생
        String performanceIsReserve = performanceInfo.getIsReserve();
        if (performanceIsReserve.equalsIgnoreCase("disable")) {
            throw new PerformanceDisable("PerformanceDisable : 공연이 예매 불가합니다.");
        }
        reservationInfo.setPerformanceInfo(performanceInfo);

        // 좌석 정보 탐색
        PerformanceSeatInfo performanceSeatInfo = commonService.getPerformanceSeatInfo(
                performanceInfo.getPerformanceId(),
                performanceInfo.getRound(),
                reservationInfo.getLine(),
                reservationInfo.getSeat()
        );
        // 좌석이 예약 불가하면 => 예외 발생
        String seatIsReserve = performanceSeatInfo.getIsReserve();
        if (seatIsReserve.equalsIgnoreCase("disable")) {
            throw new PerformanceSeatDisable("PerformanceSeatDisable : 공연 좌석이 예매 불가합니다.");
        }

        // 할인 정보, 최종 가격 정보 탐색
        Integer finalPrice = getFinalPrice(userInfo, performanceInfo, reservationInfo);

        // 예약
        // 내역 추가, 좌석 정보 수정, (좌석 매진 시) 공연 정보 수정
        Integer reservationId = addReservation(reservationInfo, performanceSeatInfo);
        reservationInfo.setReservationId(reservationId);

        // 예약 내역 반환
        return reservationInfo;
    }

    @Transactional(readOnly = true)
    public List<ReservationInfo> getUserReservation(UserInfo userInfo) {
        // 유저 정보 탐색
        UserInfo dbUserInfo = commonService.getUserInfo(userInfo.getName(), userInfo.getPhoneNumber());

        // 유저의 예약 내역 탐색
        return reservationRepository.findByUserId(dbUserInfo.getUserId())
                .stream()
                .map(r -> ReservationInfo.of(
                        r,
                        commonService.getPerformanceInfoById(r.getPerformanceId()),
                        dbUserInfo
                        ))
                .toList();
    }

    public Integer getFinalPrice(UserInfo userInfo, PerformanceInfo performanceInfo, ReservationInfo reservationInfo) {
        // 우선은 기본 가격 그대로 최종 가격 결정
        Integer performancePrice = performanceInfo.getPrice();
        Integer discountPrice = 0;
        Integer finalPrice = performancePrice - discountPrice;

        long userAmount = reservationInfo.getAmount();

        if (finalPrice > userAmount) {
            throw new PriceOver("PriceOver : 결제 금액이 부족합니다.");
        }

        return finalPrice;
    }

    public Integer addReservation(ReservationInfo reservationInfo, PerformanceSeatInfo performanceSeatInfo) {
        // 예약 내역 추가
        Integer reservationId = reservationRepository.save(Reservation.of(reservationInfo)).getId();

        // 좌석 정보 수정 (enable -> disable)
        performanceSeatInfo.setIsReserve("disable");
        performanceSeatRepository.save(PerformanceSeat.of(performanceSeatInfo));

        // 공연 정보 수정 (모든 좌석이 disable이라면 -> disable)
        List<PerformanceSeatInfo> enablePerformanceSeatInfoList = performanceSeatRepository.findByPerformanceIdAndIsReserve(
                reservationInfo.getPerformanceInfo().getPerformanceId(), "enable")
                .stream()
                .map(PerformanceSeatInfo::of)
                .toList();
        if (enablePerformanceSeatInfoList.size() == 0) {
            reservationInfo.getPerformanceInfo().setIsReserve("disable");
            performanceRepository.save(Performance.of(reservationInfo.getPerformanceInfo()));
        }

        return reservationId;
    }
}
