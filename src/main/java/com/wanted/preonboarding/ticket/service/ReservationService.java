package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReservationInfo;
import com.wanted.preonboarding.ticket.domain.dto.UserInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeat;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.User;
import com.wanted.preonboarding.ticket.exception.*;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatRepository performanceSeatRepository;

    @Transactional
    public ReservationInfo reservation(ReservationInfo reservationInfo) {
        // 유저 정보 탐색
        UserInfo userInfo = getUserInfo(
                reservationInfo.getUserInfo().getName(),
                reservationInfo.getUserInfo().getPhoneNumber()
        );
        reservationInfo.setUserInfo(userInfo);

        // 공연 정보 탐색
        PerformanceInfo performanceInfo = getPerformanceInfoByNameAndRound(
                reservationInfo.getPerformanceInfo().getPerformanceName(),
                reservationInfo.getPerformanceInfo().getRound()
        );
        reservationInfo.setPerformanceInfo(performanceInfo);

        // 좌석 정보 탐색
        PerformanceSeatInfo performanceSeatInfo = getPerformanceSeatInfo(
                performanceInfo.getPerformanceId(),
                performanceInfo.getRound(),
                reservationInfo.getLine(),
                reservationInfo.getSeat()
        );

        // 할인 정보, 최종 가격 정보 탐색
        Integer finalPrice = getFinalPrice(userInfo, performanceInfo, reservationInfo);

        // 예약
        // 내역 추가, 좌석 정보 수정, (좌석 매진 시) 공연 정보 수정
        addReservation(reservationInfo, performanceSeatInfo, performanceInfo);

        // 예약 내역 반환
        return reservationInfo;
    }

    @Transactional(readOnly = true)
    public List<ReservationInfo> getUserReservation(UserInfo userInfo) {
        // 유저 정보 탐색
        UserInfo dbUserInfo = getUserInfo(userInfo.getName(), userInfo.getPhoneNumber());

        // 유저의 예약 내역 탐색
        return reservationRepository.findByUserId(dbUserInfo.getUserId())
                .stream()
                .map(r -> ReservationInfo.of(
                        r,
                        getPerformanceInfoById(r.getPerformanceId()),
                        dbUserInfo
                        ))
                .toList();
    }

    public UserInfo getUserInfo(String name, String phoneNumber) {
        Optional<User> user = userRepository.findByNameAndPhoneNumber(name, phoneNumber);

        // 유저 정보가 있으면 => 기존 유저 ID로
        if (user.isPresent()) {
            return UserInfo.of(user.get());
        }
        // 유저 정보가 없으면 => 새로운 유저 ID로
        else {
            UserInfo newUserInfo = UserInfo.builder()
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .build();
            User newUser = User.of(newUserInfo);
            UUID userId = userRepository.save(newUser).getId();
            newUserInfo.setUserId(userId);
            return newUserInfo;
        }
    }

    public PerformanceInfo getPerformanceInfoById(UUID performanceId) {
        Optional<Performance> performance = performanceRepository.findById(performanceId);

        // 공연 정보가 없으면 => 예외 발생
        if (performance.isEmpty()) {
            throw new PerformanceNotFound("PerformanceNotFound : 공연을 찾을 수 없습니다.");
        }

        return PerformanceInfo.of(performance.get());
    }

    public PerformanceInfo getPerformanceInfoByNameAndRound(String performanceName, Integer round) {
        Optional<Performance> performance = performanceRepository.findByNameAndRound(performanceName, round);

        // 공연 정보가 없으면 => 예외 발생
        if (performance.isEmpty()) {
            throw new PerformanceNotFound("PerformanceNotFound : 공연을 찾을 수 없습니다.");
        }
        // 공연이 예매 불가하면 => 예외 발생
        else {
            String enableReserve = performance.get().getIsReserve();
            if (enableReserve.equalsIgnoreCase("disable")) {
                throw new PerformanceDisable("PerformanceDisable : 공연이 예매 불가합니다.");
            }
            else {
                return PerformanceInfo.of(performance.get());
            }
        }

    }

    public PerformanceSeatInfo getPerformanceSeatInfo(UUID performanceId, Integer round, Character line, Integer seat) {
        Optional<PerformanceSeat> performanceSeat = performanceSeatRepository.findByPerformanceIdAndRoundAndLineAndSeat(performanceId, round, line, seat);

        // 좌석 정보가 없으면 => 예외 발생
        if (performanceSeat.isEmpty()) {
            throw new PerformanceSeatNotFound("PerformanceSeatNotFound : 공연 좌석을 찾을 수 없습니다.");
        }
        // 좌석이 예약 불가하면 => 예외 발생
        else{
            String enableReserve = performanceSeat.get().getIsReserve();
            if (enableReserve.equalsIgnoreCase("disable")) {
                throw new PerformanceSeatDisable("PerformanceSeatDisable : 공연 좌석이 예매 불가합니다.");
            }
            else {
                return PerformanceSeatInfo.of(performanceSeat.get());
            }
        }

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

    public void addReservation(ReservationInfo reservationInfo, PerformanceSeatInfo performanceSeatInfo, PerformanceInfo performanceInfo) {
        // 예약 내역 추가
        reservationRepository.save(Reservation.of(reservationInfo));

        // 좌석 정보 수정 (enable -> disable)
        performanceSeatInfo.setIsReserve("disable");
        performanceSeatRepository.save(PerformanceSeat.of(performanceSeatInfo));

        // 공연 정보 수정 (모든 좌석이 disable이라면 -> disable)
        List<PerformanceSeatInfo> enablePerformanceSeatInfoList = performanceSeatRepository.findByPerformanceIdAndIsReserve(
                performanceInfo.getPerformanceId(), "enable")
                .stream()
                .map(PerformanceSeatInfo::of)
                .toList();
        if (enablePerformanceSeatInfoList.size() == 0) {
            performanceInfo.setIsReserve("disable");
            performanceRepository.save(Performance.of(performanceInfo));
        }
    }
}
