package com.wanted.preonboarding.ticket.service.dto.request;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.contoller.ReservationCheckRequestDto;
import com.wanted.preonboarding.ticket.service.ReservationService;
import com.wanted.preonboarding.ticket.service.dto.response.ReservationCheckResponseDto;
import com.wanted.preonboarding.ticket.service.dto.response.ReservationResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ResponseHandler<ReservationResponseDto>> reserve(
            @RequestBody final ReservationRequestDto reservationRequestDto) {
        log.info("reservationRequestDto: {}", reservationRequestDto);
        final ReservationResponseDto responseDto = reservationService.reserve(reservationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseHandler.<ReservationResponseDto>builder()
                        .message("Reservation Success")
                        .statusCode(HttpStatus.CREATED)
                        .data(responseDto)
                        .build()
                );
    }

    @GetMapping
    public ResponseEntity<ResponseHandler<List<ReservationCheckResponseDto>>> check(
            @RequestBody final ReservationCheckRequestDto requestDto) {
        log.info("requestDto: {}", requestDto);
        final List<ReservationCheckResponseDto> responses = reservationService.check(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseHandler.<List<ReservationCheckResponseDto>>builder()
                        .message("Reservation Check Success")
                        .statusCode(HttpStatus.OK)
                        .data(responses)
                        .build()
                );
    }
}
