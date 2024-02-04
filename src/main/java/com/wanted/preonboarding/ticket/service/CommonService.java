package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReservationInfo;
import com.wanted.preonboarding.ticket.domain.dto.UserInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeat;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.User;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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
        User user = userRepository.findByNameAndPhoneNumber(name, phoneNumber)
                .orElse(saveNewUser(name, phoneNumber));

        return UserInfo.of(user);
    }

    public User saveNewUser(String name, String phoneNumber) {
        UserInfo newUserInfo = UserInfo.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
        User newUser = User.of(newUserInfo);
        userRepository.save(newUser);
        return newUser;
    }

    public PerformanceInfo getPerformanceInfoById(UUID performanceId) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(NoSuchElementException::new);

        return PerformanceInfo.of(performance);
    }

    public PerformanceInfo getPerformanceInfoByNameAndRound(String performanceName, Integer round) {
        Performance performance = performanceRepository.findByNameAndRound(performanceName, round)
                .orElseThrow(NoSuchElementException::new);

        return PerformanceInfo.of(performance);
    }

    public PerformanceSeatInfo getPerformanceSeatInfo(UUID performanceId, Integer round, Character line, Integer seat) {
        PerformanceSeat performanceSeat = performanceSeatRepository.findByPerformanceIdAndRoundAndLineAndSeat(performanceId, round, line, seat)
                .orElseThrow(NoSuchElementException::new);

        return PerformanceSeatInfo.of(performanceSeat);
    }

    public ReservationInfo getReservationInfoById(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NoSuchElementException::new);

        return ReservationInfo.of(reservation);
    }
}
