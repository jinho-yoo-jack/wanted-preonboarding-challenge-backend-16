package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
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
    public ReserveInfo reservation(ReserveInfo reserveInfo) {
        // 유저 정보 탐색
        UserInfo userInfo = getUserInfo(
                reserveInfo.getUserInfo().getName(),
                reserveInfo.getUserInfo().getPhoneNumber()
        );
        reserveInfo.setUserInfo(userInfo);

        // 공연 정보 탐색
        PerformanceInfo performanceInfo = getPerformanceInfo(
                reserveInfo.getPerformanceInfo().getPerformanceName(),
                reserveInfo.getPerformanceInfo().getRound()
        );
        reserveInfo.setPerformanceInfo(performanceInfo);

        // 좌석 정보 탐색
        PerformanceSeatInfo performanceSeatInfo = getPerformanceSeatInfo(
                performanceInfo.getPerformanceId(),
                performanceInfo.getRound(),
                reserveInfo.getLine(),
                reserveInfo.getSeat()
        );

        // 할인 정보, 최종 가격 정보 탐색
        Integer finalPrice = getFinalPrice(userInfo, performanceInfo, reserveInfo);

        // 예약
        // 내역 추가, 좌석 정보 수정, (좌석 매진 시) 공연 정보 수정
        addReservation(reserveInfo, performanceSeatInfo, performanceInfo);

        // 예약 내역 반환
        return reserveInfo;
    }

    public UserInfo getUserInfo(String name, String phoneNumber) {
        Optional<User> user = Optional.ofNullable(userRepository.findByNameAndPhoneNumber(name, phoneNumber));

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

    public PerformanceInfo getPerformanceInfo(String performanceName, Integer round) {
        Optional<Performance> performance = Optional.ofNullable(performanceRepository.findByNameAndRound(performanceName, round));

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
        Optional<PerformanceSeat> performanceSeat = Optional.ofNullable(performanceSeatRepository.findByPerformanceIdAndRoundAndLineAndSeat(performanceId, round, line, seat));

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

    public Integer getFinalPrice(UserInfo userInfo, PerformanceInfo performanceInfo, ReserveInfo reserveInfo) {
        // 우선은 기본 가격 그대로 최종 가격 결정
        Integer performancePrice = performanceInfo.getPrice();
        Integer discountPrice = 0;
        Integer finalPrice = performancePrice - discountPrice;

        long userAmount = reserveInfo.getAmount();

        if (finalPrice > userAmount) {
            throw new PriceOver("PriceOver : 결제 금액이 부족합니다.");
        }

        return finalPrice;
    }

    public void addReservation(ReserveInfo reserveInfo, PerformanceSeatInfo performanceSeatInfo, PerformanceInfo performanceInfo) {
        // 예약 내역 추가
        reservationRepository.save(Reservation.of(reserveInfo));

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
