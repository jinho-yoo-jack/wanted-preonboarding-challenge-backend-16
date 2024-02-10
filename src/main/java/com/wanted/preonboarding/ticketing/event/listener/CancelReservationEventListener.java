package com.wanted.preonboarding.ticketing.event.listener;

import com.wanted.preonboarding.ticketing.aop.advice.exception.NotFoundPerformanceSeatInfoException;
import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import com.wanted.preonboarding.ticketing.service.dto.AlarmInfo;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.event.CancelReservationEvent;
import com.wanted.preonboarding.ticketing.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticketing.service.alarm.AlarmSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CancelReservationEventListener {
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final List<AlarmSender> alarmSenders;

    @TransactionalEventListener
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Async
    public void handleCancelEvent(CancelReservationEvent cancelReservationEvent) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository
                .findById(cancelReservationEvent.performanceSeatInfoId())
                .orElseThrow(() -> new NotFoundPerformanceSeatInfoException(ErrorCode.NOT_FOUND_PERFORMANCE_SEAT_INFO));

        sendAlarm(performanceSeatInfo, cancelReservationEvent.alarmInfos());
    }

    private void sendAlarm(PerformanceSeatInfo performanceSeatInfo, List<AlarmInfo> alarmInfos) {
        alarmSenders.forEach(alarmSender ->
                alarmInfos.forEach(alarmInfo ->
                        alarmSender.sendAlarm(alarmInfo, performanceSeatInfo)
                ));
    }
}
