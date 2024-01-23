package com.wanted.preonboarding.ticket.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long> {
}
