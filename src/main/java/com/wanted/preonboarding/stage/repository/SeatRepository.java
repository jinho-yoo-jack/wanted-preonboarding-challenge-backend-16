package com.wanted.preonboarding.stage.repository;

import com.wanted.preonboarding.stage.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}