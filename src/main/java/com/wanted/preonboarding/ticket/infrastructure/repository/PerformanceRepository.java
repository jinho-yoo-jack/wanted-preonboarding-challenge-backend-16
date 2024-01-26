package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

    /**
     * isReserve 필드의 값이 매개변수 String isReserve와 동일한 Performance 엔티티 리스트를 반환
     * @param isReserve
     * @return List<Performance>
     */
    List<Performance> findByIsReserve(String isReserve);

    /**
     * name 필드의 값이 매개변수 String name과 동일한 Performance 엔티티를 리턴
     * @param name
     * @return Performance
     */
    Performance findByName(String name);

    /**
     * Performance 엔티티에 대한 레퍼런스 객체를 리턴
     * @param id
     * @return Performance
     */
    Performance getReferenceById(UUID id);
}
