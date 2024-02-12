package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.entity.Subscription;
import com.wanted.preonboarding.ticket.domain.event.ReservationCanceledEvent;
import com.wanted.preonboarding.ticket.infrastructure.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SubscriptionRepository subscriptionRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public void notify(final ReservationCanceledEvent event){
        List<Subscription> subscriptions = subscriptionRepository.findAllByPerformanceId(event.getPerformanceId());
        for (Subscription s : subscriptions){
            String content = s.getUserInfo().getName() + "님 예약 가능한 좌석이 생겼습니다. [공연명:" + event.getPerformanceName()
                    + ", 회차:" + event.getSeatInfo().getRound()
                    + ", 좌석 열:" + event.getSeatInfo().getLine()
                    + ", 좌석 행:" + event.getSeatInfo().getSeat() + "] ";

            sendMessage(s.getUserInfo().getPhoneNumber(), content);
        }
    }

    private void sendMessage(final String phoneNumber, final String content){
        System.out.println("SEND MSG " + phoneNumber + " ===> " + content);
    }
}
