package com.wanted.preonboarding.ticket.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.domain.dto.NotificationInfo;
import com.wanted.preonboarding.ticket.domain.dto.NotificationRequest;
import com.wanted.preonboarding.ticket.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody NotificationRequest request) {
        NotificationInfo result = notificationService.subscribe(request.getUserId(), request.getPerformanceId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseHandler.builder().statusCode(HttpStatus.OK)
                        .data(result)
                        .message("Success")
                        .build());
    }
}
