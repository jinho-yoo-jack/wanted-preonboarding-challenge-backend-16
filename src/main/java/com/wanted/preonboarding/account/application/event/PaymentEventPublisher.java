package com.wanted.preonboarding.account.application.event;

import com.wanted.preonboarding.account.application.event.dto.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(PaymentInfo info) {
        applicationEventPublisher.publishEvent(new PaymentEvent(info));
    }
}
