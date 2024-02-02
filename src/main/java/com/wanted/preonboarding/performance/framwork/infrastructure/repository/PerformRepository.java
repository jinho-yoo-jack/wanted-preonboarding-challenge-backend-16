package com.wanted.preonboarding.performance.framwork.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.Perform;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformRepository extends JpaRepository<Perform, UUID> {

	Optional<Perform> findByReservationAvailable(boolean isReserve);

}
