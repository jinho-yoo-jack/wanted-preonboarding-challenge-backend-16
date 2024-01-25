package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.domain.dto.response.CancelReservationResponse;
import com.wanted.preonboarding.ticketing.domain.entity.Alarm;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmSender {
    private final AlarmRepository alarmRepository;

    public List<CancelReservationResponse> sendAlarm(PerformanceSeatInfo performanceSeatInfo) {
        List<Alarm> alarms = alarmRepository.findAllByPerformance(performanceSeatInfo.getPerformance());

        return alarms.stream().map(alarm -> alarm.toCancelReservationResponse(performanceSeatInfo))
                .collect(Collectors.toList());
    }
}