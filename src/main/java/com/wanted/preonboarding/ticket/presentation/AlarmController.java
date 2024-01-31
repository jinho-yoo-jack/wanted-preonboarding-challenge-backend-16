package com.wanted.preonboarding.ticket.presentation;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanted.preonboarding.ticket.domain.dto.SmsResponse;
import com.wanted.preonboarding.ticket.domain.dto.ReservePossibleAlarmCustomerInfoDto;
import com.wanted.preonboarding.ticket.domain.dto.ResponseDto;
import com.wanted.preonboarding.ticket.presentation.service.AlarmSmsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmSmsService alarmSmsService;


    @PostMapping("/customer/performance-seat/new")
    public ResponseEntity<ResponseDto> sendMessagePerformanceCancelCameout(@RequestBody ReservePossibleAlarmCustomerInfoDto dto) throws NoSuchAlgorithmException, URISyntaxException, UnsupportedEncodingException, InvalidKeyException, JsonProcessingException {

        ResponseDto responseDto = new ResponseDto();
        try {
            SmsResponse smsResponse = alarmSmsService.performanceCancelCameout(dto);
            responseDto.setResponse("success");
            responseDto.setMessage("취소된 예약이 있다는 알림이 성공적으로 전달했습니다.");
            responseDto.setData(smsResponse);
            return ResponseEntity.ok(responseDto);
        } catch (EntityNotFoundException e) {
            responseDto.setResponse("failed");
            responseDto.setMessage("예약 공연 정보가 없습니다.");
            responseDto.setData(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        } catch (Exception e) {
            responseDto.setResponse("failed");
            responseDto.setMessage("취소된 예약 알림 전달 중 오류가 발생했습니다.");
            responseDto.setData(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
}
