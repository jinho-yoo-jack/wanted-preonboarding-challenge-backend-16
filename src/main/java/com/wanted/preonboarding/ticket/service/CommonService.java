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

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {
    private final UserRepository userRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatRepository performanceSeatRepository;
    private final ReservationRepository reservationRepository;

    public UserInfo getUserInfo(String name, String phoneNumber) {
        Optional<User> user = userRepository.findByNameAndPhoneNumber(name, phoneNumber);

        // 유저 정보가 있으면 => 기존 유저 ID로
        if (user.isPresent()) {
            return UserInfo.of(user.get());
        }

        // 유저 정보가 없으면 => 새로운 유저 ID로
        UserInfo newUserInfo = UserInfo.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
        User newUser = User.of(newUserInfo);
        UUID userId = userRepository.save(newUser).getId();
        newUserInfo.setUserId(userId);
        return newUserInfo;
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

        return PerformanceInfo.of(performance.get());
    }

    public PerformanceSeatInfo getPerformanceSeatInfo(UUID performanceId, Integer round, Character line, Integer seat) {
        Optional<PerformanceSeat> performanceSeat = performanceSeatRepository.findByPerformanceIdAndRoundAndLineAndSeat(performanceId, round, line, seat);

        // 좌석 정보가 없으면 => 예외 발생
        if (performanceSeat.isEmpty()) {
            throw new PerformanceSeatNotFound("PerformanceSeatNotFound : 공연 좌석을 찾을 수 없습니다.");
        }

        return PerformanceSeatInfo.of(performanceSeat.get());
    }

    public ReservationInfo getReservationInfoById(Integer reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        // 예약 정보가 없으면 => 예외 발생
        if (reservation.isEmpty()) {
            throw new ReservationNotFound("ReservationNotFound : 예약 내역을 찾을 수 없습니다.");
        }

        return ReservationInfo.of(reservation.get());
    }
}
