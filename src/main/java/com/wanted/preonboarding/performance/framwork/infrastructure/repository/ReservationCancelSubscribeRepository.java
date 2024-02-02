package com.wanted.preonboarding.performance.framwork.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.ReservationCancelSubscribe;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationCancelSubscribeRepository extends JpaRepository<ReservationCancelSubscribe, UUID> {

	List<ReservationCancelSubscribe> findByPerformId(UUID id);
}
