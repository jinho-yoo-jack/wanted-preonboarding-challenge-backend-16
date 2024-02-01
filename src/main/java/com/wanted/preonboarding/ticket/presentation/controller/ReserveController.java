package com.wanted.preonboarding.ticket.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import com.wanted.preonboarding.ticket.global.exception.InvalidInputException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
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
    public ResponseEntity<BaseResDto> reservation(@RequestBody ReserveInfo info) {
        log.info("ReserveController reservation");
        boolean reserve = ticketSeller.reserve(info);
        BaseResDto baseResDto;
        if(reserve) {
            baseResDto = ticketSeller.getPerformanceInfoDetail(info);
        } else {
            baseResDto = new BaseResDto();
        }
        return ResponseEntity.ok(baseResDto);
    }
}
