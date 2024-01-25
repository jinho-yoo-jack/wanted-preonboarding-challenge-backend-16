package com.wanted.preonboarding.ticket.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
	Optional<Performance> findById(UUID id);

	List<Performance> findByIsReserve(String isReserve);

	Page<Performance> findByIsReserve(String isReserve, PageRequest pageRequest);

	Performance findByName(String name);
}
