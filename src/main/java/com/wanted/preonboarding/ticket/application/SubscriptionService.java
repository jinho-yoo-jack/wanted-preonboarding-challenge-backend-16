package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.core.domain.exception.CustomException;
import com.wanted.preonboarding.ticket.application.dto.SubscribeParam;
import com.wanted.preonboarding.ticket.application.dto.UnsubscribeParam;
import com.wanted.preonboarding.ticket.domain.entity.Subscription;
import com.wanted.preonboarding.ticket.domain.exception.TicketErrorCode;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final PerformanceRepository performanceRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public Subscription subscribe(final SubscribeParam param) {
        performanceRepository.findById(param.getPerformanceId()).orElseThrow(() -> new CustomException(TicketErrorCode.PERFORMANCE_NOT_FOUND));

        if(subscriptionRepository.existsByPerformanceIdAndUserInfo(param.getPerformanceId(), param.getUserInfo())){
            throw new CustomException(TicketErrorCode.DUPLICATED_SUBSCRIPTION);
        }

        Subscription subscription = Subscription.builder()
                .performanceId(param.getPerformanceId())
                .userInfo(param.getUserInfo())
                .build();
        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public void unsubscribe(final UnsubscribeParam param) {
        Subscription subscription = subscriptionRepository.findById(param.getSubscriptionId()).orElseThrow(() -> new CustomException(TicketErrorCode.SUBSCRIPTION_NOT_FOUND));

        if(!subscription.getUserInfo().equals(param.getUserInfo())){
            throw new CustomException(TicketErrorCode.NOT_SUBSCRIPTION_OWNER);
        }
        subscriptionRepository.delete(subscription);
    }
}
