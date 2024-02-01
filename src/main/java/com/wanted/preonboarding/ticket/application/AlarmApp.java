package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.ResponseAlarmDto;
import com.wanted.preonboarding.ticket.domain.dto.SubscriberPerformanceRequestDto;
import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.ReservationAlarm;
import com.wanted.preonboarding.ticket.infrastructure.repository.AlarmRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.SubscribeReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AlarmApp {
    private final PerformanceRepository performanceRepository;
    private final SubscribeReservationRepository subscribeReservationRepository;
    private final AlarmRepository alarmRepository;

    public void subscribeReservation(SubscriberPerformanceRequestDto requestDto){
        subscribeReservationRepository.save(ReservationAlarm.of(requestDto));
    }

    public void sendAlarm(UUID performanceId){
        List<ReservationAlarm> subscribers = subscribeReservationRepository.findByPerformanceId(performanceId);
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(EntityNotFoundException::new);
        for (ReservationAlarm receiver : subscribers) {
            alarmRepository.save(Alarm.of(receiver.getReceiver(),performance));
        }
    }

    public ResponseAlarmDto getAlarm(String memberName){
        Alarm alarm = alarmRepository.findByMemberName(memberName)
                .orElseThrow(EntityNotFoundException::new);
        return alarm.toResponseDto();
    }

}
