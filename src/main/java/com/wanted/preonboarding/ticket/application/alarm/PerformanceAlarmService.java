package com.wanted.preonboarding.ticket.application.alarm;

import com.wanted.preonboarding.ticket.domain.performance.PerformanceAlarm;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceAlarmRepository;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.dto.response.alarm.AlarmResponse;
import com.wanted.preonboarding.ticket.exception.notfound.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PerformanceAlarmService implements AlarmService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceAlarmRepository alarmRepository;

    @Override
    public boolean isTargetExist(final String targetId) {
        UUID performanceId = UUID.fromString(targetId);
        return performanceRepository.findNameById(performanceId).isPresent();
    }

    @Transactional
    @Override
    public AlarmResponse post(final String targetId, final String name, final String phone) {
        if (!isTargetExist(targetId)) throw new NotFoundException("알람 대상을 찾을 수 없습니다.");

        PerformanceAlarm alarm = PerformanceAlarm.builder()
            .performanceId(UUID.fromString(targetId))
            .name(name)
            .phoneNumber(phone)
            .build();

        return AlarmResponse.of(alarmRepository.save(alarm));
    }
}
