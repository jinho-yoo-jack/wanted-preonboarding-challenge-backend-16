package com.wanted.preonboarding.account.application.event;

import com.wanted.preonboarding.account.application.mapper.AccountReader;
import com.wanted.preonboarding.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class PaymentEventHandler {

    private final AccountReader accountReader;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void pay(PaymentEvent paymentEvent) {
        Account account = accountReader.findByUserId(paymentEvent.getData().user().getId());
        account.withdraw(paymentEvent.getData().payMoney().getAmount());
    }
}
