package com.wanted.preonboarding.ticket.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.service.ReservationService;
import com.wanted.preonboarding.ticket.service.dto.request.ReservationRequestDto;
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
}
