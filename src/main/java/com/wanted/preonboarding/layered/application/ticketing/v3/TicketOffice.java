package com.wanted.preonboarding.layered.application.ticketing.v3;

import com.wanted.preonboarding.layered.domain.dto.ReservationInfo;
import com.wanted.preonboarding.layered.domain.dto.NotificationRegister;
import com.wanted.preonboarding.layered.domain.dto.PerformanceDiscountPolicyInfo;
import com.wanted.preonboarding.layered.domain.entity.ticketing.Reservation;
import com.wanted.preonboarding.layered.infrastructure.repository.PerformanceDiscountPolicyRepository;
import com.wanted.preonboarding.layered.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.layered.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.layered.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.layered.infrastructure.repository.TicketCancelNotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TicketOffice {
    private final PerformanceRepository performanceRepository;
    private final PerformanceDiscountPolicyRepository performanceDiscountPolicyRepository;
    private final TicketCancelNotificationRepository ticketCancelNotificationRepository;
    private final ReservationRepository reservationRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    public List<NotificationRegister> getSubscribers(UUID performanceId) {
        //TODO: 특정 공연 티켓 취소 알림 구독자 가져 오기
        return ticketCancelNotificationRepository.findAllByPerformanceId(performanceId).stream().map(NotificationRegister::of).toList();
    }

    public PerformanceInfo getPerformanceInfo(UUID performanceId) throws ChangeSetPersister.NotFoundException {
        return PerformanceInfo.of(
            performanceRepository.findById(performanceId).orElseThrow(ChangeSetPersister.NotFoundException::new)
        );
    }

    public PerformanceDiscountPolicyInfo getDiscountPolicyInfoBy(UUID performanceId, String policyName) {
        return PerformanceDiscountPolicyInfo.of(performanceDiscountPolicyRepository.findByPerformanceIdAndName(performanceId, policyName));
    }

    @Transactional
    public void ticketCancelBy(ReservationInfo cancelMessage) {
        Reservation reservation = getReservationInfo(cancelMessage);
        reservation.softDelete();
        applicationEventPublisher.publishEvent(cancelMessage);
    }

    private Reservation getReservationInfo(ReservationInfo info){
        return reservationRepository.findReservationByPerformanceIdAndRoundAndGateAndLineAndSeatAndNameAndPhoneNumber(
            info.getPerformanceId(),
            info.getRound(),
            info.getGate(),
            info.getLine(),
            info.getSeat(),
            info.getUserName(),
            info.getPhoneNumber());
    }

}
