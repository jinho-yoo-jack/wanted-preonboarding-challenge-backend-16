package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Showroom;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowroomRepository extends JpaRepository<Showroom, UUID> {

}
