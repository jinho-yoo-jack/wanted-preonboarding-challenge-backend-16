package com.wanted.preonboarding.ticket.application.notification.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.annotation.ExecutionTimer;
import com.wanted.preonboarding.ticket.application.notification.service.NotificationService;
import com.wanted.preonboarding.ticket.domain.dto.request.RequestNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wanted.preonboarding.core.domain.response.ResponseHandler.MESSAGE_SUCCESS;
import static com.wanted.preonboarding.core.domain.response.ResponseHandler.createResponse;

@ExecutionTimer
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/set")
    public ResponseEntity<ResponseHandler<Void>> setNotificaton(
            @RequestBody final RequestNotification requestNotification
    ) {
        notificationService.setNotification(requestNotification);
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, null);
    }
}
