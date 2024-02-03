package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.notification.PerformanceNotificationRegister;
import com.wanted.preonboarding.ticket.dto.request.notification.PerformanceNotificationRegisterRequest;
import com.wanted.preonboarding.ticket.dto.response.alarm.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/app/alarm")
@RestController
public class NotificationController {

    private final PerformanceNotificationRegister performanceNotificationRegister;

    @PostMapping("/performance")
    public ResponseEntity<NotificationResponse> postPerformanceAlarm(@RequestBody PerformanceNotificationRegisterRequest request) {
        NotificationResponse result = performanceNotificationRegister.register(request.performanceId(), request.name(), request.phone());
        return ResponseEntity.ok(result);
    }

}
