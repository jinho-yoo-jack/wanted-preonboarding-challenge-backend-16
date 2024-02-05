package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.NotificationService;
import com.wanted.preonboarding.ticket.domain.dto.request.NotificationCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.NotificationCreateResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.NotificationFindResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<ResponseHandler<List<NotificationFindResponse>>> findAllNotification(@RequestParam UUID performanceId) {
        List<NotificationFindResponse> notificationFindResponseList = notificationService.findAllNotification(performanceId);
        return ResponseEntity.ok()
                .body(ResponseHandler.<List<NotificationFindResponse>>builder()
                        .message("success")
                        .statusCode(HttpStatus.OK)
                        .data(notificationFindResponseList)
                        .build()
                );
    }
}
