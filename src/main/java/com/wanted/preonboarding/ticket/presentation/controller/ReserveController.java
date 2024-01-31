package com.wanted.preonboarding.ticket.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.global.exception.InvalidInputException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;

    @PostMapping("/")
    public ResponseEntity<ResponseDto> reservation(@RequestBody ReserveInfo info) {
        log.info("ReserveController reservation");
        System.out.println("reservation");
        ResponseDto responseDto = new ResponseDto();
        try {
            ReserveInfo reserveInfo = reserve(info);
            PerformanceInfo performanceInfoDetail = ticketSeller.getPerformanceInfoDetail(info);
            responseDto.setResponse("success");
            responseDto.setMessage("예약을 성공적으로 완료했습니다.");
            ResponseReserveQueryDto reserveResponseQueryDto = getResponseQueryDto(reserveInfo, performanceInfoDetail);
            responseDto.setData(ReserveSystemDto.builder()
                    .responseQueryDto(reserveResponseQueryDto)
                    .build());
            return ResponseEntity.ok(responseDto);
        } catch (InvalidInputException e) {
            responseDto.setResponse("failed");
            responseDto.setMessage("예약 공연 정보가 없습니다.");
            responseDto.setData(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        } catch (Exception e) {
            responseDto.setResponse("failed");
            responseDto.setMessage("예약하는 중 오류가 발생했습니다.");
            responseDto.setData(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }




    private static ResponseReserveQueryDto getResponseQueryDto(ReserveInfo reserveInfo, PerformanceInfo performanceInfoDetail) {
        ResponseReserveQueryDto reserveResponseQueryDto = ResponseReserveQueryDto
                .builder()
                .performanceId(reserveInfo.getPerformanceId())
                .round(reserveInfo.getRound())
                .performanceName(performanceInfoDetail.getPerformanceName())
                .gate(reserveInfo.getGate())
                .line(reserveInfo.getLine())
                .seat(reserveInfo.getSeat())
                .reservationName(reserveInfo.getReservationName())
                .reservationPhoneNumber(reserveInfo.getReservationPhoneNumber())
                .build();
        return reserveResponseQueryDto;
    }


    private ReserveInfo reserve(ReserveInfo info) {
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(info.getPerformanceId()) // 예약을 원하는 공연 또는 전시회ID
                .reservationName(info.getReservationName()) // 고객의 이름
                .reservationPhoneNumber(info.getReservationPhoneNumber()) // 휴대 전화
                .reservationStatus(info.getReservationStatus()) // 예약; 취소;
                .amount(info.getAmount()) // 결제 가능한 금액(잔고)
                .round(info.getRound()) // 회차
                .line(info.getLine()) // 좌석 정보
                .seat(info.getSeat()) // 좌석 정보
                .build();

        ticketSeller.reserve(reserveInfo);

        return reserveInfo;
    }
}
