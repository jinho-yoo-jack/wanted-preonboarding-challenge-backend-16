package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.AlarmApp;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmApp alarmApp;

    @PostMapping("/")
    public void subscribeAlarm(SubscriberPerformanceRequestDto requestDto) throws Exception {
        alarmApp.subscribeReservation(requestDto);
    }

    @GetMapping("/")
    public ResponseAlarmDto getAlarm(String memberName) throws Exception {
        return alarmApp.getAlarm(memberName);
    }



}
