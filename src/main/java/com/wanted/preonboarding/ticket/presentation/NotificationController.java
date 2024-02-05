package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.NotificationService;
import com.wanted.preonboarding.ticket.domain.dto.request.NotificationCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.NotificationCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ResponseHandler<NotificationCreateResponse>> saveNotification(@RequestBody NotificationCreateRequest request) {
        NotificationCreateResponse notificationCreateResponse = notificationService.saveNotification(request);
        return ResponseEntity.ok()
                .body(ResponseHandler.<NotificationCreateResponse>builder()
                        .message("created")
                        .statusCode(HttpStatus.CREATED)
                        .data(notificationCreateResponse)
                        .build()
                );
    }
}
