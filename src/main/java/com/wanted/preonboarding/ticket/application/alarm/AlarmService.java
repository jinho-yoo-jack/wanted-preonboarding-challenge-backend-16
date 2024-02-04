package com.wanted.preonboarding.ticket.application.alarm;

import com.wanted.preonboarding.ticket.application.alarm.sms.SmsService;
import com.wanted.preonboarding.ticket.domain.dto.request.ReserverInfoRequest;
import com.wanted.preonboarding.ticket.domain.entity.ReservationCancelAlarm;
import com.wanted.preonboarding.ticket.domain.entity.SeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationCancelAlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AlarmService {
    private final ReservationCancelAlarmRepository reservationCancelAlarmRepository;
    private final SmsService smsService;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registerCancelAlarm(String performanceId, ReserverInfoRequest reserverInfo)
    {
        reservationCancelAlarmRepository.save
                (
                    ReservationCancelAlarm.builder()
                            .performanceId(performanceId)
                            .name(reserverInfo.getName())
                            .phoneNumber(reserverInfo.getPhoneNumber())
                            .build()
                );
    }
    public void sendCancelAlarm(String performanceId, SeatInfo seatInfo)
    {
        List<ReservationCancelAlarm> cancelAlarmList = reservationCancelAlarmRepository.findAllByPerformanceId(performanceId);
        smsService.sendMultipleSms(
                cancelAlarmList.stream().map(ReservationCancelAlarm::getPhoneNumber).collect(Collectors.toList()),
                makeCancelReservationMsg(performanceId, seatInfo)
        );
    }
    private String makeCancelReservationMsg(String performanceId, SeatInfo seatInfo)
    {
        return "[performanceId]:"+performanceId+" / " +
                "[좌석 정보]: gate-"+seatInfo.getGate()+" "+
                "line-"+seatInfo.getLine()+" "+
                "seat-"+seatInfo.getSeat()+" "+
                "round-"+seatInfo.getRound()
                +"\n 좌석이 예약 취소 되어 현재 예약 가능 상태입니다.";
    }
}