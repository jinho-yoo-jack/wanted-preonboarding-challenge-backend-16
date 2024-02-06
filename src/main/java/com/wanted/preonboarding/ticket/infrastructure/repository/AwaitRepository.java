package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.application.dto.AwaitInfo;

import java.util.List;
import java.util.UUID;

public interface AwaitRepository {

    AwaitInfo save(AwaitInfo awaitInfo);

    List<AwaitInfo> findByPerformanceId(UUID performanceId);
}
