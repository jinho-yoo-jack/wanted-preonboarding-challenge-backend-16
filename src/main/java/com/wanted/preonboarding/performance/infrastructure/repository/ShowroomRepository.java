package com.wanted.preonboarding.performance.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.PerformanceShowroom;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowroomRepository extends JpaRepository<PerformanceShowroom, UUID> {

}
