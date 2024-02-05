package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeat;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.exception.*;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final CommonService commonService;
    private final AlarmService alarmService;
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
        int finalPrice = getFinalPrice(userInfo, performanceInfo, reservationInfo);

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

    @Transactional
    public ReservationInfo cancelReservation(ReservationInfo reservationInfo) {
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
        // 공연 시작일이 이미 지났다면 => 예외 발생
        checkPerformanceDateValidateForCancel(performanceInfo);
        reservationInfo.setPerformanceInfo(performanceInfo);

        // 예약 내역 탐색
        // 예약 내역 내, 유저 정보 및 공연 정보 일치 여부 확인
        ReservationInfo dbReservationInfo = commonService.getReservationInfoById(reservationInfo.getReservationId());
        if (!dbReservationInfo.getUserInfo().getUserId().equals(userInfo.getUserId())) {
            throw new InvalidReservation("InvalidReservation : 예약자 정보가 일치하지 않습니다.");
        }
        if (!dbReservationInfo.getPerformanceInfo().getPerformanceId().equals(performanceInfo.getPerformanceId())) {
            throw new InvalidReservation("InvalidReservation : 예약 공연 정보가 일치하지 않습니다.");
        }

        // 좌석 정보 탐색
        PerformanceSeatInfo performanceSeatInfo = commonService.getPerformanceSeatInfo(
                performanceInfo.getPerformanceId(),
                performanceInfo.getRound(),
                dbReservationInfo.getLine(),
                dbReservationInfo.getSeat()
        );
        reservationInfo.setGate(performanceSeatInfo.getGate());
        reservationInfo.setLine(performanceSeatInfo.getLine());
        reservationInfo.setSeat(performanceSeatInfo.getSeat());

        // 예약
        // 내역 삭제, 좌석 정보 수정, (좌석 매진 취소 시) 공연 정보 수정
        deleteReservation(reservationInfo, performanceSeatInfo);

        // 해당 공연 알림 신청 유저에게 알림 발송
        alarmService.sendAlarm(reservationInfo);

        // 예약 취소 내역 반환
        return reservationInfo;
    }

    private int getFinalPrice(UserInfo userInfo, PerformanceInfo performanceInfo, ReservationInfo reservationInfo) {
        // 우선은 기본 가격 그대로 최종 가격 결정
        int performancePrice = performanceInfo.getPrice();
        int discountPrice = 0;
        int finalPrice = performancePrice - discountPrice;

        int userAmount = reservationInfo.getAmount();

        if (finalPrice > userAmount) {
            throw new PriceOver("PriceOver : 결제 금액이 부족합니다.");
        }

        return finalPrice;
    }

    private Integer addReservation(ReservationInfo reservationInfo, PerformanceSeatInfo performanceSeatInfo) {
        // 예약 내역 추가
        Integer reservationId = reservationRepository.save(Reservation.of(reservationInfo)).getId();

        // 좌석 정보 수정 (enable -> disable)
        IsReserveType enable = IsReserveType.ENABLE;
        IsReserveType disable = IsReserveType.DISABLE;

        performanceSeatInfo.setIsReserve(disable.getText());
        performanceSeatRepository.save(PerformanceSeat.of(performanceSeatInfo));

        // 공연 정보 수정 (모든 좌석이 disable이라면 -> disable)
        List<PerformanceSeatInfo> enablePerformanceSeatInfoList = performanceSeatRepository.findByPerformanceIdAndIsReserve(
                reservationInfo.getPerformanceInfo().getPerformanceId(), enable.getText())
                .stream()
                .map(PerformanceSeatInfo::of)
                .toList();
        if (enablePerformanceSeatInfoList.size() == 0) {
            reservationInfo.getPerformanceInfo().setIsReserve(disable.getText());
            performanceRepository.save(Performance.of(reservationInfo.getPerformanceInfo()));
        }

        return reservationId;
    }

    private void checkPerformanceDateValidateForCancel(PerformanceInfo performanceInfo) {
        Timestamp startDate = performanceInfo.getStartDate();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        int comparisonResult = currentTimestamp.compareTo(startDate);

        if (comparisonResult > 0) {
            throw new CancelTimeOver("CancelTimeOver : 취소 가능한 기간이 지났습니다.");
        }
    }

    private void deleteReservation(ReservationInfo reservationInfo, PerformanceSeatInfo performanceSeatInfo) {
        // 예약 내역 삭제 (=> db 트리거로 canceledReservation 에 저장됨)
        reservationRepository.deleteById(reservationInfo.getReservationId());

        // 좌석 정보 수정 (disable -> enable)
        IsReserveType enable = IsReserveType.ENABLE;
        IsReserveType disable = IsReserveType.DISABLE;

        performanceSeatInfo.setIsReserve(enable.getText());
        performanceSeatRepository.save(PerformanceSeat.of(performanceSeatInfo));

        // 공연 정보 수정
        // 현재 공연이 매진(disable) 상태인데, 취소로 enable 좌석이 남아있다면 -> enable
        PerformanceInfo performanceInfo = reservationInfo.getPerformanceInfo();
        if (performanceInfo.getIsReserve().equals(disable.getText())) {
            List<PerformanceSeatInfo> enablePerformanceSeatInfoList = performanceSeatRepository.findByPerformanceIdAndIsReserve(
                            performanceInfo.getPerformanceId(), enable.getText())
                    .stream()
                    .map(PerformanceSeatInfo::of)
                    .toList();
            if (enablePerformanceSeatInfoList.size() > 0) {
                performanceInfo.setIsReserve(enable.getText());
                performanceRepository.save(Performance.of(performanceInfo));
            }
        }

    }
}
