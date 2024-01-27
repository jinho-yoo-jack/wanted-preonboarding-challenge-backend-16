package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long>, PerformanceSeatInfoRepository {
}
