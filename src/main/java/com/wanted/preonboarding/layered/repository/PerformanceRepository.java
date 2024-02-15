package com.wanted.preonboarding.layered.repository;

import com.wanted.preonboarding.domain.entity.Performance;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link Performance} Entity를 다루기 위한 JPA 레포지토리 인터페이스입니다.
 *
 * <p>기본적인 CRUD 연산 및 특정 예약 상태의 공연을 조회하거나,
 * 공연의 이름을 통해 공연의 정보를 조회할 수 있는 커스텀 메서드를 제공합니다.</p>
 */
public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
  List<Performance> findByIsReserve(String isReserve);

  List<Performance> findAllByName(String name);

  Performance findByName(String name);
}
