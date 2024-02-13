package com.wanted.preonboarding.hexagonal.account.adapter.out.web.persistence.repository;

import com.wanted.preonboarding.hexagonal.account.adapter.out.web.persistence.entity.PerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface SpringDataPerformanceRepository extends JpaRepository<PerformanceEntity, UUID> {
    List<PerformanceEntity> findByIsReserve(String isReserve);

    PerformanceEntity findByName(String name);
}
