package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.DiscountPolicy;
import com.wanted.preonboarding.ticket.domain.PerformanceIsReserve;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.exception.AlreadyReservationException;
import com.wanted.preonboarding.ticket.domain.exception.NotEnoughAmountException;
import com.wanted.preonboarding.ticket.infrastructure.repository.NotificationRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.presentation.request.CreateReserveRequest;
import com.wanted.preonboarding.ticket.presentation.request.ReadReservationRequest;
import com.wanted.preonboarding.ticket.presentation.request.ReserveNotificationReqeust;
import com.wanted.preonboarding.ticket.presentation.response.CreateReservationResponse;
import com.wanted.preonboarding.ticket.presentation.response.GetAllPerformanceInfoListResponse;
import com.wanted.preonboarding.ticket.presentation.response.ReadReservationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final DiscountPolicy discountPolicy;
    private final NotificationRepository notificationRepository;
    private long totalAmount = 0L;

    public List<ReadReservationResponse> getReservations(ReadReservationRequest readReservationRequest) {
        return reservationRepository.findReservationAndPerformance(readReservationRequest.getReservationName(), readReservationRequest.getReservationPhoneNumber())
                .stream()
                .map(ReadReservationResponse::of)
                .toList();
    }

    public List<GetAllPerformanceInfoListResponse> getAllPerformanceInfoList(PerformanceIsReserve isReserve) {
        return performanceRepository.findByIsReserve(isReserve.name())
                .stream()
                .map(GetAllPerformanceInfoListResponse::of)
                .toList();
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    @Transactional
    public CreateReservationResponse reserve(CreateReserveRequest createReserveRequest) {
        log.info("reserveInfo ID => {}", createReserveRequest.getPerformanceId());
        Performance info = performanceRepository.findById(createReserveRequest.getPerformanceId())
                .orElseThrow(EntityNotFoundException::new);
        String enableReserve = info.getIsReserve();

        long userAmount = createReserveRequest.getAmount();
        int discountPerformanceAmount = discountPolicy.calculateDiscountAmount(info.getPrice());

        validation(enableReserve, userAmount, discountPerformanceAmount);

        // 1. 결제
        createReserveRequest.setAmount(userAmount - discountPerformanceAmount);
        // 2. 예매 진행
        reservationRepository.save(Reservation.of(createReserveRequest));

        return CreateReservationResponse.of(
                info.getRound(),
                info.getName(),
                createReserveRequest.getLine(),
                createReserveRequest.getSeat(),
                info.getId(),
                createReserveRequest.getReservationName(),
                createReserveRequest.getReservationPhoneNumber()
        );

    }

    @Transactional
    public UUID reserveNotification(ReserveNotificationReqeust reserveNotificationReqeust) {
        UUID performanceId = reserveNotificationReqeust.getPerformanceId();
        String phoneNumber = reserveNotificationReqeust.getPhoneNumber();

        boolean existPerformance = performanceRepository.existsById(performanceId);
        if (!existPerformance) {
            throw new EntityNotFoundException("해당 공연이 존재하지 않습니다.");
        }

        notificationRepository.save(Notification.of(performanceId, phoneNumber));
        return performanceId;
    }

    @Transactional
    public List<Notification> cancelReservation(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(EntityNotFoundException::new);

        reservationRepository.delete(reservation);

        List<Notification> notifications = notificationRepository.findAllByPerformanceId(reservation.getPerformanceId());

        notifications.forEach(notification -> notification.chagneIsSendTrue());

        return notifications;
    }

    private void validation(String enableReserve, long userAmount, int discountPerformanceAmount) {
        if (!enableReserve.equalsIgnoreCase("enable")) {
            throw new AlreadyReservationException("이미 예약된 공연입니다.");
        }


        if (userAmount < discountPerformanceAmount) {
            throw new NotEnoughAmountException("잔액이 부족합니다.");
        }
    }

    @Async
    public void sendReservationMessage(Notification notification) {
        // TODO: 메시지 보내는 로직 구현
        System.out.println("메시지 보냄, notification=" + notification);
    }

}
