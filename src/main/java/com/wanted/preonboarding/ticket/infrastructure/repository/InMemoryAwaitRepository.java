package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.application.dto.AwaitInfo;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryAwaitRepository implements AwaitRepository {

    private final Map<UUID, List<AwaitInfo>> awaitStorage = new ConcurrentHashMap<>();

    @Override
    public AwaitInfo save(AwaitInfo awaitInfo) {
        if (awaitStorage.containsKey(awaitInfo.performanceId())) {
            awaitStorage.get(awaitInfo.performanceId()).add(awaitInfo);
            return awaitInfo;
        }

        awaitStorage.put(awaitInfo.performanceId(), new ArrayList<>());
        awaitStorage.get(awaitInfo.performanceId()).add(awaitInfo);

        return awaitInfo;
    }

    @Override
    public List<AwaitInfo> findByPerformanceId(UUID performanceId) {
        if (!awaitStorage.containsKey(performanceId)) {
            return Collections.emptyList();
        }

        return awaitStorage.get(performanceId);
    }
}
