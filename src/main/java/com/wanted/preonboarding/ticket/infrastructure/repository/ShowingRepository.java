package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Showing;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowingRepository extends JpaRepository<Showing, UUID> {

	Optional<Showing> findByReservationAvailable(boolean isReserve);

	Showing findByPerformanceName(String name);
}
