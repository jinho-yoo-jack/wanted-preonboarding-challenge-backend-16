package com.wanted.preonboarding.ticket.infrastructure.repository;
import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface AlarmRepository extends JpaRepository<Alarm, UUID> {

    List<Alarm> findByPerformanceId(UUID uuid);
}
