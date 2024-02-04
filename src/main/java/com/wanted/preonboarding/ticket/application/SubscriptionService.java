package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.application.dto.SubscribeParam;
import com.wanted.preonboarding.ticket.application.dto.UnsubscribeParam;
import com.wanted.preonboarding.ticket.domain.entity.Subscription;
import com.wanted.preonboarding.ticket.domain.exception.BadRequestException;
import com.wanted.preonboarding.ticket.domain.exception.ForbiddenException;
import com.wanted.preonboarding.ticket.domain.exception.NotFoundException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService {

    private final PerformanceRepository performanceRepository;

    private final SubscriptionRepository subscriptionRepository;

    public Subscription subscribe(final SubscribeParam param) {
        performanceRepository.findById(param.getPerformanceId()).orElseThrow(() -> new NotFoundException("공연이 존재하지 않습니다."));

        if(subscriptionRepository.existsByPerformanceIdAndUserInfo(param.getPerformanceId(), param.getUserInfo())){
            throw new BadRequestException("이미 구독하고 있는 공연입니다.");
        }

        Subscription subscription = Subscription.builder()
                .performanceId(param.getPerformanceId())
                .userInfo(param.getUserInfo())
                .build();
        return subscriptionRepository.save(subscription);
    }

    public void unsubscribe(final UnsubscribeParam param) {
        Subscription subscription = subscriptionRepository.findById(param.getSubscriptionId()).orElseThrow(() -> new NotFoundException("구독이 존재하지 않습니다."));

        if(!subscription.getUserInfo().equals(param.getUserInfo())){
            throw new ForbiddenException("예약 취소 권한이 없습니다.");
        }
        subscriptionRepository.delete(subscription);
    }
}
