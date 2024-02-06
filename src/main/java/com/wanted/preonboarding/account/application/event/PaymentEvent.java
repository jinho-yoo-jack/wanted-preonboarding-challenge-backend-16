package com.wanted.preonboarding.account.application.event;

import com.wanted.preonboarding.account.application.event.dto.PaymentInfo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PaymentEvent extends ApplicationEvent {

    private final PaymentInfo data;

    public PaymentEvent(PaymentInfo paymentInfo) {
        super(paymentInfo);
        this.data = paymentInfo;
    }
}
