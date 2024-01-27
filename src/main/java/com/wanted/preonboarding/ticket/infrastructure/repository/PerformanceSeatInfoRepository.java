package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;

import java.util.Optional;

public interface PerformanceSeatInfoRepository {

    Optional<PerformanceSeatInfo> findById(Long id);
}
