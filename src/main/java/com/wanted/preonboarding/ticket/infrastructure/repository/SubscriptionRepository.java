package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Subscription;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    boolean existsByPerformanceIdAndUserInfo(UUID performanceId, UserInfo userInfo);
    List<Subscription> findAllByPerformanceId(UUID performanceId);
}
