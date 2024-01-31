package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.ticket.domain.dto.AlarmInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.UserInfo;
import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import com.wanted.preonboarding.ticket.exception.AlarmDuplicated;
import com.wanted.preonboarding.ticket.infrastructure.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {
    private final CommonService commonService;
    private final AlarmRepository alarmRepository;

    @Transactional
    public AlarmInfo registAlarm(AlarmInfo alarmInfo) {
        // 유저 정보 탐색
        UserInfo userInfo = commonService.getUserInfo(
                alarmInfo.getUserInfo().getName(),
                alarmInfo.getUserInfo().getPhoneNumber()
        );
        alarmInfo.setUserInfo(userInfo);

        // 공연 정보 탐색
        PerformanceInfo performanceInfo = commonService.getPerformanceInfoByNameAndRound(
                alarmInfo.getPerformanceInfo().getPerformanceName(),
                alarmInfo.getPerformanceInfo().getRound()
        );
        alarmInfo.setPerformanceInfo(performanceInfo);

        // 중복 알림 확인 (공연 ID, 유저 ID)
        checkAlarmDuplicated(alarmInfo);

        // 알림
        // 내역 추가
        Integer alarmId = addAlarm(alarmInfo);
        alarmInfo.setAlarmId(alarmId);

        // 알림 내역 반환
        return alarmInfo;
    }

    public Integer addAlarm(AlarmInfo alarmInfo) {
        // 알림 내역 추가
        return alarmRepository.save(Alarm.of(alarmInfo)).getId();
    }

    public void checkAlarmDuplicated(AlarmInfo alarmInfo) {
        Optional<Alarm> alarm = alarmRepository.findByUserIdAndPerformanceId(
                alarmInfo.getUserInfo().getUserId(),
                alarmInfo.getPerformanceInfo().getPerformanceId()
        );

        // 알림 정보가 이미 있으면 => 중복 불가, 예외 발생
        if (alarm.isPresent()) {
            throw new AlarmDuplicated("AlarmDuplicated : 유저 한 명당, 한 공연에 알림은 한 개만 등록할 수 있습니다.");
        }
    }

}
