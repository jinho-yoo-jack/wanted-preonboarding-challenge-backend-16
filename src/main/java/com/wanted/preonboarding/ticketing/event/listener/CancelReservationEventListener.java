package com.wanted.preonboarding.ticketing.event.listener;

import com.wanted.preonboarding.ticketing.aop.advice.exception.NotFoundPerformanceSeatInfoException;
import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import com.wanted.preonboarding.ticketing.domain.entity.Alarm;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.event.CancelReservationEvent;
import com.wanted.preonboarding.ticketing.repository.AlarmRepository;
import com.wanted.preonboarding.ticketing.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticketing.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CancelReservationEventListener {
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final AlarmRepository alarmRepository;
    private final EmailSender emailSender;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCancelEvent(CancelReservationEvent cancelReservationEvent) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository
                .findById(cancelReservationEvent.getPerformanceSeatInfoId())
                .orElseThrow(() -> new NotFoundPerformanceSeatInfoException(ErrorCode.NOT_FOUND_PERFORMANCE_SEAT_INFO));

        List<Alarm> alarms = alarmRepository.findAllByPerformance(performanceSeatInfo.getPerformance());

        for (Alarm alarm : alarms) {
            emailSender.sendPerformanceInfo(alarm.getEmail(), performanceSeatInfo);
        }

        alarmRepository.deleteAll(alarms);
    }
}
