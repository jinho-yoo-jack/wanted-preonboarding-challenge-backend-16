package com.wanted.preonboarding.ticketing.repository;

import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long> {
}
