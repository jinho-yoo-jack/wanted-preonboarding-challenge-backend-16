package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {

    Optional<PerformanceSeatInfo> findById(Integer id);
}
