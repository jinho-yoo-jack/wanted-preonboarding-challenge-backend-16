package com.wanted.preonboarding.ticket.infra.entity

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PerformanceSeatInfoEntityJpaRepository : JpaRepository<PerformanceSeatInfoEntity, Long> {
    fun findByPerformanceId(performanceId: UUID): List<PerformanceSeatInfoEntity>

    fun findAllByPerformanceIdIn(performanceIds: List<UUID>): List<PerformanceSeatInfoEntity>
}
