package com.wanted.preonboarding.ticketing.service.alarm;

import com.wanted.preonboarding.ticketing.service.dto.AlarmInfo;
import com.wanted.preonboarding.ticketing.controller.request.CreateAlarmRequest;
import com.wanted.preonboarding.ticketing.controller.response.CreateAlarmResponse;
import com.wanted.preonboarding.ticketing.domain.entity.Alarm;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.repository.AlarmRepository;
import com.wanted.preonboarding.ticketing.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    private final PerformanceService performanceService;

    @Transactional
    public CreateAlarmResponse createAlarm(CreateAlarmRequest createAlarmRequest) {
        Performance performance = performanceService.findPerformance(createAlarmRequest.getPerformanceId());
        Alarm alarm = saveAlarm(createAlarmRequest, performance);

        return alarm.toCreateAlarmResponse();
    }

    private Alarm saveAlarm(CreateAlarmRequest createAlarmRequest, Performance performance) {
        Alarm alarm = createAlarmRequest.from(performance);

        return alarmRepository.save(alarm);
    }

    public List<AlarmInfo> deleteAndRetrieveAlarmInfo(PerformanceSeatInfo performanceSeatInfo) {
        List<Alarm> alarms = alarmRepository.findAllByPerformance(performanceSeatInfo.getPerformance());
        List<AlarmInfo> alarmInfos = alarms.stream()
                .map(Alarm::toAlarmInfo)
                .collect(Collectors.toList());

        alarmRepository.deleteAllInBatch(alarms);

        return alarmInfos;
    }
}
