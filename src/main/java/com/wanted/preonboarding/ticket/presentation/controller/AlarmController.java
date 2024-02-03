package com.wanted.preonboarding.ticket.presentation.controller;


import com.wanted.preonboarding.ticket.domain.dto.ReservePossibleAlarmCustomerInfoDto;
import com.wanted.preonboarding.ticket.presentation.service.AlarmMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmMailService alarmMailService;

    /**
     * 예약 가능 알림 서비스
     * 특정 공연에 대해서 취소 건이 발생하는 경우, 알림 신청을 해놓은 고객에게 취소된 예약이 있다는 사실을 알리는 알림 서비스
     * Send Message: 공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보
     * @param dto
     * @return
     */
    @PostMapping("/send")
    public void sendAlarm(@RequestBody ReservePossibleAlarmCustomerInfoDto dto) {
        alarmMailService.sendAlarmPerformance(dto);
    }

}
