package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.core.code.NoticeType;
import com.wanted.preonboarding.core.code.ReservationStatus;
import com.wanted.preonboarding.ticket.controller.model.ReservationApplyModel;
import com.wanted.preonboarding.ticket.controller.model.request.ReservationApplyRequest;
import com.wanted.preonboarding.ticket.domain.Notice;
import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.domain.PerformanceSeat;
import com.wanted.preonboarding.ticket.domain.Reservation;
import com.wanted.preonboarding.ticket.repository.NoticeRepository;
import com.wanted.preonboarding.ticket.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.repository.PerformanceSeatRepository;
import com.wanted.preonboarding.ticket.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.service.discount.FixDiscount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.wanted.preonboarding.core.code.ErrorMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatRepository performanceSeatRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public ReservationApplyModel apply(ReservationApplyRequest request) {
        Performance performance = performanceRepository.findById(request.getPerformanceId())
                .orElseThrow(() -> new IllegalArgumentException(EMPTY_DATA.getDescription()));

        validApply(request, performance);

        Reservation saveReservation = reservationRepository.save(
                Reservation.create(
                        request.getUserName(),
                        request.getPhoneNumber(),
                        performanceRepository.getReferenceById(request.getPerformanceId()),
                        performanceSeatRepository.getReferenceById(request.getPerformanceSeatId())
        ));

        // 할인 정책 적용 (만원 이상 구매 시 천원 할인)
        saveReservation.discount(new FixDiscount());

        return ReservationApplyModel.of(saveReservation);

    }

    private void validApply(ReservationApplyRequest request, Performance performance) {
        if (!performance.isReserve()) {
            throw new IllegalStateException(RESERVATION_FAIL.getDescription());
        }

        if (performance.getPrice().compareTo(request.getAmountAvailable()) > 0) {
            throw new IllegalStateException(OVER_PRICE.getDescription());
        }
    }


    @Transactional(readOnly = true)
    public List<ReservationApplyModel> getReservations(String userName, String phoneNumber) {
        List<Reservation> reservations = reservationRepository.findAllByUserNameAndPhoneNumber(userName, phoneNumber);
        return reservations.stream()
                .map(ReservationApplyModel::of)
                .toList();
    }

    @Transactional
    public Long cancel(Long id, String userName, String phoneNumber) {
        Reservation reservation = reservationRepository.findByIdAndUserNameAndPhoneNumber(id, userName, phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException(EMPTY_DATA.getDescription()));

        if (reservation.getStatus() != ReservationStatus.APPLY) {
            throw new IllegalStateException(CANCEL_STATUS_APPLY.getDescription());
        }

        reservation.cancel();
        noticeCancel(reservation.getPerformance(), reservation.getPerformanceSeat());
        return reservation.getId();
    }

    private void noticeCancel(Performance performance, PerformanceSeat performanceSeat) {
        try {
            List<Notice> notices = noticeRepository.findAllByTypeAndPerformance(NoticeType.CANCEL_NOTICE, performance);

            // 실제 알림 발송이 아닌 log 대체
            for (Notice notice : notices) {
                log.info("{}에게 {} 번호로 알림 발송", notice.getUserName(), notice.getPhoneNumber());
                log.info("메시지 : {}", getMessage(performance, performanceSeat));
                log.info("=====================");
            }
            log.info("");
        } catch (Exception e) { // 알림 발송에서 실패 발생 시 트랜잭션 롤백 되지 않도록 처리(에러 로그만 출력)
            log.error(e.getMessage());
        }
    }

    private String getMessage(Performance performance, PerformanceSeat performanceSeat) {
        return String.format("-공연ID: %s\n -공연명: %s\n -회차: %d\n -시작일시: %tF(yyyy-MM-dd)\n -예매 가능 좌석 정보: %s",
                performance.getId(), performance.getTitle(), performance.getRound(), performance.getStartDate(), performanceSeat.getSeat());
    }
}
