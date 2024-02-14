package com.wanted.preonboarding.hexagonal.account.adapter.out.web.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.hexagonal.account.adapter.out.web.persistence.entity.PerformanceEntity;

public interface SpringDataPerformanceRepository extends JpaRepository<PerformanceEntity, UUID>{
	
	List<PerformanceEntity> findByIsReserve(String isReserve);

    PerformanceEntity findByName(String name);
}
