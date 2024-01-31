package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.SubscriptionService;
import com.wanted.preonboarding.ticket.presentation.dto.ReservationResponse;
import com.wanted.preonboarding.ticket.presentation.dto.SubscribeRequest;
import com.wanted.preonboarding.ticket.presentation.dto.SubscribeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<ResponseHandler<SubscribeResponse>> subscribe(@RequestBody SubscribeRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseHandler.<SubscribeResponse>builder()
                        .statusCode(HttpStatus.CREATED)
                        .message("Success")
                        .data(SubscribeResponse.of(subscriptionService.subscribe(request.toDto())))
                        .build()
                );
    }
//
//    @PostMapping
//    public ResponseEntity<ResponseHandler<SubscriptionResponse>> unsubscribe(@RequestBody ReservationCreateRequest request) {
//        return ResponseEntity.noContent().build();
//    }
}
